package com.office.ticketreserve.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    public String pThumbImgUpload(@RequestParam("thum_img") MultipartFile thumbImg) {
		
		// RestTemplate 객체 생성
		RestTemplate restTemplate = new RestTemplate();
		
		// Request Header 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		// Request body 설정
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("file", thumbImg.getResource());		
		
		// Request Entity 생성
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
		
		// API 호출
		String serverURL = "http://14.42.124.93:8091/upload_p_thum";
		ResponseEntity<String> response = restTemplate.postForEntity(serverURL, requestEntity, String.class);
		
		// 응답에서 파일 경로 추출
		String filePath= response.getBody();

		// 파일 경로 반환
		return filePath;
    }
}

