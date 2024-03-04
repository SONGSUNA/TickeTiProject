package com.office.worldcup.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("ticket_reservation")
public class ReservationController {
	
	@GetMapping({"/", ""})
	public String step1() {
		log.info("[ReservationController] step1()");
		
		String nextPage = "ticket_reservation/ticket_reservation_step1";
		
		return nextPage;
	}
	
	@GetMapping({"/step2"})
	public String step2() {
		log.info("[ReservationController] step2()");
		
		String nextPage = "ticket_reservation/ticket_reservation_step2";
		
		return nextPage;
	}
	
	@GetMapping({"/step3"})
	public String step3() {
		log.info("[ReservationController] step3()");
		
		String nextPage = "ticket_reservation/ticket_reservation_step3";
		
		return nextPage;
	}
	
	@GetMapping({"/step4"})
	public String home() {
		log.info("[ReservationController] step4()");
		
		String nextPage = "ticket_reservation/ticket_reservation_step4";
		
		return nextPage;
	}
	
} 
