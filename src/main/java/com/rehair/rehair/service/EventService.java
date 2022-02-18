package com.rehair.rehair.service;

import com.rehair.rehair.domain.Event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class EventService {
    
    public String getFullPath(String filename) {
    	String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\";
		return projectPath + filename;
	}
   
    public Event upload(MultipartFile multipartFile) throws IOException {
    	if (multipartFile.isEmpty()) {
			return null;
		}
    	
    	String originalFilename = multipartFile.getOriginalFilename();
    	String uuid = UUID.randomUUID().toString();
    	String serverFilename = uuid + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    	multipartFile.transferTo(new File(getFullPath(serverFilename)));
    	return new Event(originalFilename, serverFilename);
    }
}
