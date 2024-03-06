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
	@GetMapping("/musical")
	public String goMusical() {
		log.info("goMusicalPage");
		
		String nextPage = "/category/musical_page";
		
		return nextPage;
	
	}
	@GetMapping("/theater")
	public String goTheater() {
		log.info("goTheaterPage");
		
		String nextPage = "/category/theater_page";
		
		return nextPage;
	
	}
	@GetMapping("/classic")
	public String goClassic() {
		log.info("goClassicPage");
		
		String nextPage = "/category/classic_page";
		
		return nextPage;
	
	}
	@GetMapping("/exhibition")
	public String goExhibition() {
		log.info("goExhibitionPage");
		
		String nextPage = "/category/exhibition_page";
		
		return nextPage;
	
	}
}
