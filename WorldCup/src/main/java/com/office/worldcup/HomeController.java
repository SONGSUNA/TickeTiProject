package com.office.worldcup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/worldcup")
public class HomeController {

	@GetMapping({"/", ""})
	public String home() {
		log.info("[HomeController] home()");
		
		String nextPage = "/home";
		
		return nextPage;
	}
	
}
