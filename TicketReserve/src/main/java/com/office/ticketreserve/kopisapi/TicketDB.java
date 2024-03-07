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
    	
    	log.info("TicketStringFix>>>" + perfomanceDtos.get(2).getP_ticket());
    	
		String Ticket = perfomanceDtos.get(2).getP_ticket();
		Ticket = Ticket.replaceAll(",", "");
		Ticket = Ticket.replaceAll(" ", "");
		
		List<String> ticketType = new ArrayList<>();
		List<Integer> ticketPrice = new ArrayList<>();
		
//		for(int i = 0; i<6; i++) {
//			if(Ticket.contains("무료")) {
//				ticketType.add(Ticket);
//				ticketPrice.add(0);
//				break;
//			} else {
//				Ticket.
//			}
//		}
		
		for(int i = 0; i < ticketType.size() - 6; i++) {
			ticketType.add("null");
			ticketPrice.add(0);
		}
		
		log.info("TicketStringFix>>>" + Ticket);
//    	for(int i = 0; i < perfomanceDtos.size(); i++) {
//
//    		
//    		
//    		ticketDto.setP_id(perfomanceDtos.get(i).getP_id());
//    		
//    		ticketDtos.add(ticketDto);
//    	}
    	
    }

	    
}
