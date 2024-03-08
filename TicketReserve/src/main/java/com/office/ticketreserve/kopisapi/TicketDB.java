package com.office.ticketreserve.kopisapi;

import java.util.ArrayList;
import java.util.List;

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
	
	String[] dayMapping = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일", };
	
    public void TicketStringFix() {
    	log.info("TicketStringFix()");
    	
    	List<PerfomanceDto> perfomanceDtos = apiDao.selectPerfomanceAll();    	
    	    	
    	for(int i = 0; i < perfomanceDtos.size(); i++) {
    		String time = perfomanceDtos.get(i).getP_time();
        	time = time.replaceAll(" ", "");
        	time = time.replaceAll("\\),", "\\)");
        	
        	List<String> times = new ArrayList<>();
        	List<String> timesinfo = new ArrayList<>();
        	

    		while(true) {
    			int idx = time.indexOf(')');
    			times.add(time.substring(0, idx+1));
    			time = time.substring(idx+1);
    			if(time.equals(""))
    				break;
    		}
    		
    		log.info("times>>>>>" + times);
    		
    		for(int j = 0; j < times.size(); j++) {			
    			if(times.get(j).contains("~")) {
    				String day = times.get(j).replaceAll("요일", "");
    				day = day.replaceAll("~", "");
    				int fN = dayToNum(day.substring(0, 1));
    				int eN = dayToNum(day.substring(1, 2));
    				
    				List<Integer> days = new ArrayList<>();
    				
    				days.add(fN);
    				for(int k = fN + 1; k < eN; k++) {
    					days.add(k);
    				}
    				days.add(eN);
    				
    				String tmpTime = times.get(j).substring(times.get(j).indexOf('('));
    				List<String> timeList = new ArrayList<>();

    				tmpTime = tmpTime.replace("(", "").replace(")", "");
    				
    				//시간 분리
    				if(tmpTime.contains(",")) {
    					while(true) {
    						
    						int idx = tmpTime.indexOf(",");
    						timeList.add(tmpTime.substring(0, idx));
    						tmpTime = tmpTime.substring(idx + 1);
    						if(!tmpTime.contains(","))
    							break;
    					}
    					timeList.add(tmpTime);
    				}
    				//요일 나열
    				for(int k = 0; k < days.size(); k++) {
    					if(timeList.size() > 0) {
    						for(int l = 0; l < timeList.size(); l++)
    							timesinfo.add(dayMapping[days.get(k)-1] + timeList.get(l));
    					} else {
    						timesinfo.add(dayMapping[days.get(k)-1] + tmpTime);
    					}
    				}
    			} else {
    				String tmpTime = times.get(j).substring(times.get(j).indexOf('('));
    				log.info("times2>>>>>" + times);
    				List<String> timeList = new ArrayList<>();

    				tmpTime = tmpTime.replace("(", "").replace(")", "");
    				log.info("tmpTime>>>>>" + tmpTime);
    				
    				if(tmpTime.contains(",")) {
    					if(times.get(j).contains("일"))
    						times.set(j, times.get(j).substring(0, times.get(j).indexOf("일") + 1));
    					if(times.get(j).contains("HOL"))
    						times.set(j, times.get(j).substring(0, times.get(j).indexOf("L") + 1));
    					log.info("times.get(i)>>>>>" + times.get(j));
    					while(true) {
    						
    						int idx = tmpTime.indexOf(",");
    						timeList.add(tmpTime.substring(0, idx));
    						tmpTime = tmpTime.substring(idx + 1);
    						if(!tmpTime.contains(","))
    							break;
    					}
    					timeList.add(tmpTime);
    				}
    				
    				times.set(j, times.get(j).replace("(", "").replace(")", ""));
    				if(timeList.size() > 0) {
    					for(int k = 0; k < timeList.size(); k++) {
    						timesinfo.add(times.get(j) + timeList.get(k));
    					}
    				} else {
    					timesinfo.add(times.get(j));
    				}
    			}
    		}
    		log.info(timesinfo);

    		
    		String ticket = perfomanceDtos.get(i).getP_ticket();
    		ticket = ticket.replaceAll(",", "");
    		ticket = ticket.replaceAll(" ", "");
    		
    		List<String> ticketType = new ArrayList<>();
    		List<Integer> ticketPrice = new ArrayList<>();
    		
    		for(int j = 0; j<6; j++) {
    			if(ticket.contains("무료")) {
    				ticketType.add(ticket);
    				ticketPrice.add(0);
    				break;
    			} else {
    				if(ticket.equals(""))
    					break;
    				
    				if(ticket.contains("권")) {
    					int idx = ticket.indexOf('권');
        				ticketType.add(ticket.substring(0, idx+1));
        				ticket = ticket.substring(idx+1);
    				}else if(ticket.contains("석")) {
    					int idx = ticket.indexOf('석');
        				ticketType.add(ticket.substring(0, idx+1));
        				ticket = ticket.substring(idx+1);
    				}
    				if(ticket.contains(")")) {
    					int idx = ticket.indexOf(')');
        				ticketType.set(j, ticketType.get(j) + ticket.substring(0, idx+1));
        				ticket = ticket.substring(idx+1);
    				}
    				
    				int idx = ticket.indexOf('원');
    				ticketPrice.add(Integer.parseInt(ticket.substring(0, idx)));
    				ticket = ticket.substring(idx+1);
    			}
    		}
    		
    		int maxIndex = ticketType.size();
    		
    		for(int j = 0; j < 7 - maxIndex; j++) {
    			ticketType.add("null");
    			ticketPrice.add(0);
    		}
    		ticketDto.setP_id(perfomanceDtos.get(i).getP_id());    		
    		ticketDto.setT_seattype_1(ticketType.get(0));
    		ticketDto.setT_seattype_2(ticketType.get(1));
    		ticketDto.setT_seattype_3(ticketType.get(2));
    		ticketDto.setT_seattype_4(ticketType.get(3));
    		ticketDto.setT_seattype_5(ticketType.get(4));
    		ticketDto.setT_seattype_6(ticketType.get(5));
    		ticketDto.setT_price_1(ticketPrice.get(0));	
    		ticketDto.setT_price_2(ticketPrice.get(1));	
    		ticketDto.setT_price_3(ticketPrice.get(2));	
    		ticketDto.setT_price_4(ticketPrice.get(3));	
    		ticketDto.setT_price_5(ticketPrice.get(4));	
    		ticketDto.setT_price_6(ticketPrice.get(5));
    		ticketDto.setT_p_date(timesinfo.toString());
    		apiDao.insertTicket(ticketDto);
    	}
    	
    }

	private int dayToNum(String day) {
		log.info("dayToNum()");
		
		int result = 0;
		
		if(day.equals("월"))
			result = 1;
		else if(day.equals("화"))
			result = 2;
		else if(day.equals("수"))
			result = 3;
		else if(day.equals("목"))
			result = 4;
		else if(day.equals("금"))
			result = 5;
		else if(day.equals("토"))
			result = 6;
		else if(day.equals("일"))
			result = 7;
		return result;
	}

	    
}
