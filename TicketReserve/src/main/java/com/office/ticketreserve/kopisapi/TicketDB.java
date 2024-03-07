package com.office.ticketreserve.kopisapi;

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
    	
    	List<PerfomanceDto> perfomanceDto = apiDao.selectPerfomanceAll();
    	
    }

	    
}
