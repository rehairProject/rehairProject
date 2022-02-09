package com.rehair.rehair.service;

import com.rehair.rehair.domain.Event;
import com.rehair.rehair.repository.EventRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
	
    private final EventRepository eventRepository;

    public void save(Event event, MultipartFile file) throws Exception {
        //저장할 경로 지정
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files";
        //랜덤이름
        UUID uuid = UUID.randomUUID();
        //랜덤이름+파일이름
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        event.setFilename(fileName);
        event.setFilepath("/files/"+fileName);
        event.setUsername("USER");
        eventRepository.save(event);
    }
}
