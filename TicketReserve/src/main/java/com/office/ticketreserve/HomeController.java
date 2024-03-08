package com.office.ticketreserve;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.ticketreserve.kopisapi.KopisApi;
import com.office.ticketreserve.kopisapi.TicketDB;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("")
public class HomeController  {
	@Autowired
	KopisApi api;
	@Autowired
	TicketDB tDB;
	
	@GetMapping({"/", ""})
	public String home() throws JAXBException {
		log.info("[HomeController] home()");
		
		String nextPage = "/home";
		api.getMusicalInfo(3);		//인자값 1주면 전체 API땡겨옴 2주면 예매현황 땡겨옴 3주면 그냥 넘어감.
		tDB.TicketStringFix();
		return nextPage;
	}
	
}
