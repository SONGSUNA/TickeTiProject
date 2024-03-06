package com.office.ticketreserve.kopisapi;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class KopisApi {

	
	@Autowired
	ApiDao apiDao; 
	
	PerfomanceDto dto = new PerfomanceDto();

	
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

    	
        return null;
    }

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
	}

	private List<KopisdetailDB> getDetailInfo(List<String> perfoList) throws JAXBException {
    	// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getDetailInfo()");
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

    	return dbList;
		
	}
    
}
