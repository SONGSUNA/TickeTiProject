package com.office.ticketreserve.kopisapi;

import java.io.StringReader;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
=======
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.office.ticketreserve.productpage.PerfomanceDto;
=======
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.office.ticketreserve.productpage.perfomanceDto;
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class KopisApi {
	@Autowired
<<<<<<< HEAD
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ApiDao apiDao; 
	
	PerfomanceDto dto = new PerfomanceDto();
=======
	perfomanceDto dto;
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb
	
    public String getMusicalInfo() throws JAXBException {
    	// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getMusicalInfo()");

    	// RestTemplate 객체 생성
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	String apiUrl = ApiConfig.API_URL + "pblprfr?service=" + ApiConfig.API_KEY + "&stdate=20230303&eddate=20240303&cpage=1&rows=1000&newsql=Y";

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
    	
    	List<String> perfoList = new ArrayList<>();
<<<<<<< HEAD

    	for(int i = 0; i<dbList.size(); i++) {
    		perfoList.add(dbList.get(i).getMt20id());
    	}
    	
    	
    	List<KopisdetailDB> detailList = getDetailInfo(perfoList);
    	List<Map<String, String>> placeInfo = getPlaceInfo(detailList);

    	for(int i = 0; i<detailList.size(); i++) {
    		String seatscaleString = placeInfo.get(i).get("seatscale");
		    int seatscale = Integer.parseInt(seatscaleString);
    		String telno = placeInfo.get(i).get("telno");
    		
    		dto.setPId(detailList.get(i).getMt20id());
    		dto.setPName(detailList.get(i).getPrfnm());
    		dto.setPStartDate(detailList.get(i).getPrfpdfrom());
    		dto.setPEndDate(detailList.get(i).getPrfpdto());
    		dto.setPGrade(detailList.get(i).getPrfage());
    		dto.setPTheater(detailList.get(i).getFcltynm());
    		dto.setPPlace(detailList.get(i).getArea());
    		dto.setPThum(detailList.get(i).getPoster());
    		dto.setPCategory(detailList.get(i).getGenrenm());
    		dto.setPMaxReserve(seatscale);
    		dto.setPNowReserve(0);
    		dto.setPRunningTime(detailList.get(i).getPrfruntime());
    		dto.setPCharacters(detailList.get(i).getPrfcast());
    		dto.setPTicket(detailList.get(i).getPcseguidance());
    		dto.setPLike(0);
    		dto.setPDetailCautions(detailList.get(i).getDtguidance());
    		dto.setPDetailImg(detailList.get(i).getStyurls().toString());
    		dto.setPAgencyInfo(detailList.get(i).getEntrpsnmA());
    		dto.setPHost(detailList.get(i).getEntrpsnmH());
    		dto.setPInquiry(telno);
    		
    		int result = apiDao.insertPerfomance(dto);
    		
    		
    		if(result != 1)
    			log.info(i + "번째 결과" + result);
    	}
=======
    	perfoList.add("PF236640");

    	for(int i = 0; i<dbList.size(); i++) {
    		perfoList.add(dbList.get(i).getMt20id());
    		
    	}
    	
    	log.info(">>>>>>>>>>>>>>" + perfoList.get(0));
    	
    	List<KopisdetailDB> detailList = getDetailInfo(perfoList);
    	List<KopisdetailDB> placeList = getPlaceInfo(detailList);
    	
//
//    	for(int i = 0; i<detailList.size(); i++) {
//    		dto.setPId(detailList.get(i).getMt20id());
//    		dto.setPName(detailList.get(i).getPrfnm());
//    		dto.setPStartDate(detailList.get(i).getPrfpdfrom());
//    		dto.setPEndDate(detailList.get(i).getPrfpdto());
//    		dto.setPGrade(detailList.get(i).getPrfage());
//    		dto.setPTheater(detailList.get(i).getFcltynm());
//    		dto.setPPlace(detailList.get(i).getArea());
//    		dto.setPThum(detailList.get(i).getPoster());
//    		dto.setPCategory(detailList.get(i).getGenrenm());
//    		dto.setPMaxReserve(/* 설정할 값 */);
//    		dto.setPNowReserve(0);
//    		dto.setPRunningTime(detailList.get(i).getPrfruntime());
//    		dto.setPCharacters(detailList.get(i).getPrfcast());
//    		dto.setPTicket(detailList.get(i).getPcseguidance());
//    		dto.setPLike(0);
//    		dto.setPDetailCautions(detailList.get(i).getDtguidance());
//    		dto.setPDetailImg(detailList.get(i).getStyurls().toString());
//    		dto.setPAgencyInfo(detailList.get(i).getEntrpsnmA());
//    		dto.setPHost(detailList.get(i).getEntrpsnmH());
//    		dto.setPInquiry(/* 설정할 값 */);
//    	}
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb
    	
        return null;
    }

<<<<<<< HEAD
	private List<Map<String, String>> getPlaceInfo(List<KopisdetailDB> perfoList) throws JAXBException {
		// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getPlaceInfo()");
    	
    	List<Map<String, String>> placeInfoList = new ArrayList<>();
    	
    	for(int i =0; i < perfoList.size(); i++) {
    		// RestTemplate 객체 생성
        	RestTemplate restTemplate = new RestTemplate();

        	// API 호출을 위한 URL 생성
        	String apiUrl = ApiConfig.API_URL + "prfplc/" + perfoList.get(i).getMt10id() + "?service=" + ApiConfig.API_KEY + "&newsql=Y";

        	// 생성된 API URL을 로그에 출력
        	log.info("apiUrl>>>" + apiUrl);

        	// API 호출하여 응답을 문자열로 받음
        	String response = restTemplate.getForObject(apiUrl, String.class);

        	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
        	JAXBContext jaxbContext = JAXBContext.newInstance(KopisPlaceDBs.class);
        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        	KopisPlaceDBs parsedObject = (KopisPlaceDBs) unmarshaller.unmarshal(new StringReader(response));

        	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
        	List<KopisPlaceDB> dbList = parsedObject.getDbList();
        	log.info("place>>>>>>>>>>>>>>" + dbList.toString());for (KopisPlaceDB placeDB : dbList) {
                Map<String, String> placeInfo = new HashMap<>();
                placeInfo.put("seatscale", placeDB.getSeatscale());
                placeInfo.put("telno", placeDB.getTelno());
                placeInfoList.add(placeInfo);
            }
        }
        
        return placeInfoList;
=======
	private List<KopisdetailDB> getPlaceInfo(List<KopisdetailDB> perfoList) throws JAXBException {
		// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getPlaceInfo()");

    	// RestTemplate 객체 생성
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	String apiUrl = ApiConfig.API_URL + "prfplc/" + perfoList.get(0).getMt10id() + "?service=" + ApiConfig.API_KEY + "&newsql=Y";

    	// 생성된 API URL을 로그에 출력
    	log.info("apiUrl>>>" + apiUrl);

    	// API 호출하여 응답을 문자열로 받음
    	String response = restTemplate.getForObject(apiUrl, String.class);

    	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
    	JAXBContext jaxbContext = JAXBContext.newInstance(KopisdetailDBs.class);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	KopisdetailDBs parsedObject = (KopisdetailDBs) unmarshaller.unmarshal(new StringReader(response));

    	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
    	List<KopisdetailDB> dbList = parsedObject.getDbList();
    	log.info("place>>>>>>>>>>>>>>" + dbList.toString());
    	
    	return dbList;
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb
	}

	private List<KopisdetailDB> getDetailInfo(List<String> perfoList) throws JAXBException {
    	// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getDetailInfo()");
<<<<<<< HEAD
    	
    	List<KopisdetailDB> dbList = new ArrayList<>();
    	
    	for(int i = 0; i<perfoList.size(); i++) {
			// RestTemplate 객체 생성
	    	RestTemplate restTemplate = new RestTemplate();
	
	    	// API 호출을 위한 URL 생성
	    	String apiUrl = ApiConfig.API_URL + "pblprfr/" + perfoList.get(i) + "?service=" + ApiConfig.API_KEY + "&newsql=Y";
	
	    	// 생성된 API URL을 로그에 출력
	    	log.info("apiUrl>>>" + apiUrl);
	
	    	// API 호출하여 응답을 문자열로 받음
	    	String response = restTemplate.getForObject(apiUrl, String.class);
	
	    	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
	    	JAXBContext jaxbContext = JAXBContext.newInstance(KopisdetailDBs.class);
	    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    	KopisdetailDBs parsedObject = (KopisdetailDBs) unmarshaller.unmarshal(new StringReader(response));
	    	
	    	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
	    	
    		dbList.add(parsedObject.getDbList().get(0));
    		
    		log.info(dbList.get(i).getMt20id());
    	}
=======

    	// RestTemplate 객체 생성
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	String apiUrl = ApiConfig.API_URL + "pblprfr/" + perfoList.get(0) + "?service=" + ApiConfig.API_KEY + "&newsql=Y";

    	// 생성된 API URL을 로그에 출력
    	log.info("apiUrl>>>" + apiUrl);

    	// API 호출하여 응답을 문자열로 받음
    	String response = restTemplate.getForObject(apiUrl, String.class);

    	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
    	JAXBContext jaxbContext = JAXBContext.newInstance(KopisdetailDBs.class);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	KopisdetailDBs parsedObject = (KopisdetailDBs) unmarshaller.unmarshal(new StringReader(response));

    	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
    	List<KopisdetailDB> dbList = parsedObject.getDbList();
    	log.info("detail>>>>>>>>>>>>>>" + dbList.toString());
    	
>>>>>>> a8d98aed552eec962811401283344596bbdc51cb
    	return dbList;
		
	}
    
}
