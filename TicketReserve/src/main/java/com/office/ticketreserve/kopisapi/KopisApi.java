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

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class KopisApi {

	
	@Autowired
	ApiDao apiDao; 
	
	@Autowired
	PerfomanceDto perfomanceDto;
	
	@Autowired
	CurrentReserveDto currentReserveDto;
	

	
	
    public String getMusicalInfo() throws JAXBException {
    	// 로그에 "getMusicalInfo()" 메시지 출력
    	log.info("getMusicalInfo()");

    	// RestTemplate 객체 생성
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	//String apiUrl = ApiConfig.API_URL + "pblprfr?service=" + ApiConfig.API_KEY + "&stdate=20240301&eddate=20240630&cpage=1&rows=1000&newsql=Y";
    	String apiUrl = ApiConfig.API_URL + "boxoffice?service=" + ApiConfig.API_KEY +  "&ststype=week&date=20240301&catecode=GGGA";
    	
    	// 생성된 API URL을 로그에 출력
    	log.info("apiUrl>>>" + apiUrl);

    	// API 호출하여 응답을 문자열로 받음
    	String response = restTemplate.getForObject(apiUrl, String.class);

    	// JAXBContext를 사용하여 XML을 Java 객체로 언마샬링
    	JAXBContext jaxbContext = JAXBContext.newInstance(KopisReserveDBs.class);
    	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    	KopisReserveDBs parsedObject = (KopisReserveDBs) unmarshaller.unmarshal(new StringReader(response));
    	
    	List<KopisReserveDB> dbList = parsedObject.getDbList();
    	
    	List<List<String>> CurrentReserve = new ArrayList<>();
    	
    	for(int i = 0; i < 7; i++) {
    		List<String> tmpList = new ArrayList<>();
    		
    		tmpList.add(dbList.get(i).getRnum() + "");
    		tmpList.add(dbList.get(i).getPrfpd());
    		tmpList.add(dbList.get(i).getMt20id());
    		
    		CurrentReserve.add(tmpList);
    	}
    	
    	for(int i = 0; i < 7; i++) {
    		if(!apiDao.checkPerfomance(CurrentReserve.get(i))) {
    			List<KopisdetailDB> listDB = getDetailInfo(CurrentReserve.get(i).get(2));
    			List<Map<String, String>> placeInfo = getPlaceInfo(listDB);
    			
    			valueToDto(listDB, placeInfo);
    		}
    		
    		perfomanceDto = apiDao.selectPerfomance(CurrentReserve.get(i).get(2));
    		
    		currentReserveDto.setP_name(perfomanceDto.getP_name());
    		currentReserveDto.setC_r_rank(Integer.parseInt(CurrentReserve.get(i).get(0)));
    		currentReserveDto.setC_r_period(CurrentReserve.get(i).get(1));
    		currentReserveDto.setP_id(perfomanceDto.getP_id());
    		currentReserveDto.setP_thum(perfomanceDto.getP_thum());
    		currentReserveDto.setP_theater(perfomanceDto.getP_theater());
    		currentReserveDto.setP_category(perfomanceDto.getP_category());
    		
    		int resutl = apiDao.insertCurrentReserve(currentReserveDto);
    		
    		if(resutl > 0)
    			log.info("실패 : " + CurrentReserve.get(i).get(2));
    	}
//    	// 언마샬링된 객체에서 KopisDB 목록을 가져옴
//    	List<KopisDB> dbList = parsedObject.getDbList();
    	
//    	List<String> perfoList = new ArrayList<>();
//
//    	for(int i = 0; i<dbList.size(); i++) {
//	    	if(dbList.get(i).getMt20id().equals("PF236543") || dbList.get(i).getMt20id().equals("PF236050"))
//	    		continue;
//	    	
//    		perfoList.add(dbList.get(i).getMt20id());
//    	}
    	
//    	
//    	List<KopisdetailDB> detailList = getDetailInfo(perfoList);
//    	List<Map<String, String>> placeInfo = getPlaceInfo(detailList);
//
//    	valueToDto(detailList, placeInfo);

    	
        return null;
    }

	private List<KopisdetailDB> getDetailInfo(String id) throws JAXBException {
		log.info("getDetailInfo()");
    	List<KopisdetailDB> dbList = new ArrayList<>();
    	
    	RestTemplate restTemplate = new RestTemplate();

    	// API 호출을 위한 URL 생성
    	String apiUrl = ApiConfig.API_URL + "pblprfr/" + id + "?service=" + ApiConfig.API_KEY + "&newsql=Y";

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
		return dbList;
	}

	private void valueToDto(List<KopisdetailDB> detailList, List<Map<String, String>> placeInfo) {
    	log.info("valueToDto()");
    	
		for(int i = 0; i<detailList.size(); i++) {
    		String seatscaleString = placeInfo.get(i).get("seatscale");
		    int seatscale = Integer.parseInt(seatscaleString);
    		String telno = placeInfo.get(i).get("telno");
    		
        	log.info("toString" + detailList.get(i).getStyurls().toString());
    		    		
        	
    		perfomanceDto.setP_id(detailList.get(i).getMt20id());
    		perfomanceDto.setP_name(detailList.get(i).getPrfnm());
    		perfomanceDto.setP_start_date(detailList.get(i).getPrfpdfrom());
    		perfomanceDto.setP_end_date(detailList.get(i).getPrfpdto());
    		perfomanceDto.setP_grade(detailList.get(i).getPrfage());
    		perfomanceDto.setP_theater(detailList.get(i).getFcltynm());
    		perfomanceDto.setP_place(detailList.get(i).getArea());
    		perfomanceDto.setP_thum(detailList.get(i).getPoster());
    		perfomanceDto.setP_category(detailList.get(i).getGenrenm());
    		perfomanceDto.setP_max_reserve(seatscale);
    		perfomanceDto.setP_now_reserve(0);
    		perfomanceDto.setP_running_time(detailList.get(i).getPrfruntime());
    		perfomanceDto.setP_characters(detailList.get(i).getPrfcast());
    		perfomanceDto.setP_ticket(detailList.get(i).getPcseguidance());
    		perfomanceDto.setP_like(0);
    		perfomanceDto.setP_detail_cautions(detailList.get(i).getDtguidance());
    		if(detailList.get(i).getStyurls().toString().equals(""))
    			continue;
			perfomanceDto.setP_detail_img(detailList.get(i).getStyurls().toString());
    		perfomanceDto.setP_agency_info(detailList.get(i).getEntrpsnmA());
    		perfomanceDto.setP_host(detailList.get(i).getEntrpsnmH());
    		perfomanceDto.setP_inquiry(telno);    			
    		
    		int result = apiDao.insertPerfomance(perfomanceDto);
    		
    		
    		if(result != 1)
    			log.info(i + "번째 결과" + result);
    	}
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
