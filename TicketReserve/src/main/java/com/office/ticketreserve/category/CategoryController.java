package com.office.ticketreserve.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/category")
@Log4j2
public class CategoryController {
	
	@GetMapping("/concert")
	public String goConcert() {
		log.info("goConcertPage");
		
		String nextPage = "/category/concert_page";
		
		return nextPage;
		
		
}
}
