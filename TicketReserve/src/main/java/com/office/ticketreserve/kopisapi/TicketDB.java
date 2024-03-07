package com.office.ticketreserve.kopisapi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.office.ticketreserve.config.TicketDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class TicketDB {
	@Autowired
	ApiDao apiDao; 
	
	@Autowired
	PerfomanceDto perfomanceDto;
	
	@Autowired
	TicketDto ticketDto;
	
    public void TicketStringFix() throws JAXBException {
    	log.info("TicketStringFix()");
    	
    	List<PerfomanceDto> perfomanceDtos = apiDao.selectPerfomanceAll();
    	List<TicketDto> ticketDtos = new ArrayList<>();
    	
    	String time = perfomanceDtos.get(0).getP_time();
    	time = time.replaceAll(" ", "");
    	time = time.replaceAll("\\),", "\\)");
    	
    	log.info(time);
    	
    	List<String> times = new ArrayList<>();
    	

		while(true) {
			int idx = time.indexOf(')');
			times.add(time.substring(0, idx+1));
			time = time.substring(idx+1);
			
			log.info(time);
			
			if(time.equals(""))
				break;
		}
		
		for(int i = 0; i < times.size(); i++) {
			if(times.get(i).contains("~")) {
				String day = times.get(i).replaceAll("요일", "");
				day = day.replaceAll("~", "");
				
				int fN = dayToNum(day.charAt(0));
				int eN = dayToNum(day.charAt(1));
				
				List<Integer> days = new ArrayList<>();
				days.add(fN);
				for(int j = fN + 1; j < eN; j++) {
					days.add(j);
				}
				days.add(eN);		
				
				
				
				log.info(days);
				
			}
		}
		
		 
    	log.info(times);
    	
    	
//    	for(int i = 0; i < perfomanceDtos.size(); i++) {
//    		String ticket = perfomanceDtos.get(i).getP_ticket();
//    		ticket = ticket.replaceAll(",", "");
//    		ticket = ticket.replaceAll(" ", "");
//    		
//    		List<String> ticketType = new ArrayList<>();
//    		List<Integer> ticketPrice = new ArrayList<>();
//    		
//    		for(int j = 0; j<6; j++) {
//    			if(ticket.contains("무료")) {
//    				ticketType.add(ticket);
//    				ticketPrice.add(0);
//    				break;
//    			} else {
//    				if(ticket.equals(""))
//    					break;
//    				
//    				int idx = ticket.indexOf('석');
//    				ticketType.add(ticket.substring(0, idx+1));
//    				ticket = ticket.substring(idx+1);
//    				idx = ticket.indexOf('원');
//    				ticketPrice.add(Integer.parseInt(ticket.substring(0, idx)));
//    				ticket = ticket.substring(idx+1);
//    			}
//    		}
//    		
//    		for(int j = 0; j < 7 - ticketType.size(); j++) {
//    			ticketType.add("null");
//    			ticketPrice.add(0);
//    		}
//    		
//    		ticketDto.setP_id(perfomanceDtos.get(i).getP_id());    		
//    		ticketDto.setT_seattype_1(ticketType.get(0));
//    		ticketDto.setT_seattype_2(ticketType.get(1));
//    		ticketDto.setT_seattype_3(ticketType.get(2));
//    		ticketDto.setT_seattype_4(ticketType.get(3));
//    		ticketDto.setT_seattype_5(ticketType.get(4));
//    		ticketDto.setT_price_1(ticketPrice.get(0));	
//    		ticketDto.setT_price_2(ticketPrice.get(1));	
//    		ticketDto.setT_price_3(ticketPrice.get(2));	
//    		ticketDto.setT_price_4(ticketPrice.get(3));	
//    		ticketDto.setT_price_5(ticketPrice.get(4));	
//    		ticketDto.setT_price_6(ticketPrice.get(5));
//    		ticketDto.setT_p_date(perfomanceDtos);
//    		
//    		ticketDtos.add(ticketDto);
//    	}
    	
    }

	private int dayToNum(char day) {
		log.info("dayToNum()");
		int result = 0;
		switch (day) {
			case '월': {
				result = 1;
			}
			case '화': {
				result = 2;
			}
			case '수': {
				result = 3;
			}
			case '목': {
				result = 4;
			}
			case '금': {
				result = 5;
			}
			case '토': {
				result = 6;
			}
			case '일': {
				result = 7;
			}
		}
		
		return result;
	}

	    
}
