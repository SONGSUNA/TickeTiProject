package com.office.worldcup.kopisapi;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KopisApi {

	
    public String getMusicalInfo() throws JAXBException {
    	// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getMusicalInfo()");

    	// RestTemplate 객체 생성
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	String apiUrl = ApiConfig.API_URL + "pblprfr?service=" + ApiConfig.API_KEY + "&stdate=20240201&eddate=20240303&cpage=1&rows=10&shcate=GGGA&signgucode=11&prfstate=02&&newsql=Y";

    	// 생성된 API URL을 로그에 출력
    	log.info("apiUrl>>>" + apiUrl);

    	// API 호출하여 응답을 문자열로 받음
    	String response = restTemplate.getForObject(apiUrl, String.class);

    	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
    	JAXBContext jaxbContext = JAXBContext.newInstance(KopisDBs.class);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	KopisDBs parsedObject = (KopisDBs) unmarshaller.unmarshal(new StringReader(response));

    	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
    	List<KopisDB> dbList = parsedObject.getDbList();

    	// KopisDB 목록을 로그에 출력
    	log.info(dbList.toString()); 
       
        
        return null;
    }
    
}
