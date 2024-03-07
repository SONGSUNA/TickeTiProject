package com.office.ticketreserve;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.ticketreserve.kopisapi.KopisApi;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("")
public class HomeController  {
	@Autowired
	KopisApi api;
	
	@GetMapping({"/", ""})
	public String home() throws JAXBException {
		log.info("[HomeController] home()");
		
		String nextPage = "/home";
		//api.getMusicalInfo();
		return nextPage;
	}
	
}
