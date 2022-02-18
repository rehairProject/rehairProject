package com.rehair.rehair.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EventDto {

	private String username; // 추후 세션처리
	private String title;
	private String content;
	private MultipartFile imageFile;
	
}
