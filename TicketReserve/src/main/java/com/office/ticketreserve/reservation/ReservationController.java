package com.office.ticketreserve.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping({"/step1"})
	public String step1(HttpSession session, Model model) {
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
	
	@GetMapping("/getInfoForReservation")
	@ResponseBody
	public Map<String, Object> getInfoForReservation(@RequestParam("p_id") String p_id) {
		log.info("[ReservationController] getInfoForReservation()");

		return reservationService.getInfoForReservation(p_id);
	}
	
	@PostMapping("/saveDateTime")
	@ResponseBody
	public String saveDateTime(@RequestParam("date") String date,
								@RequestParam("time") String time,
								@RequestParam("p_id") String p_id,
								HttpSession session) {
		log.info("[ReservationController] saveDateTime()");
		
		session.setAttribute("selectedDate", date);
	    session.setAttribute("selectedTime", time);
	    session.setAttribute("p_id", p_id);
	    
	    return "success";
	}
	
	@GetMapping("/getDateTime")
	@ResponseBody
	public HashMap<String, Object> getDateTime(HttpSession session) {
		log.info("[ReservationController] getDateTime()");
		
		String selectedDate = (String) session.getAttribute("selectedDate");
		String selectedTime = (String) session.getAttribute("selectedTime");
		String p_id = (String) session.getAttribute("p_id");
		
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("selectedDate", selectedDate);
		hashMap.put("selectedTime", selectedTime);
		hashMap.put("p_id", p_id);
		
		return hashMap;
	}
	
	@GetMapping("/getReserveSeat")
	@ResponseBody
	public List<Integer> getReserveSeat(@RequestParam("t_no") String t_no,
										 @RequestParam("selectedDate") String selectedDate,
										 @RequestParam("selectedTimeInfo") String selectedTimeInfo) {
		log.info("[ReservationController] getReserveSeat()");
		
		return reservationService.getReserveSeat(t_no, selectedDate, selectedTimeInfo);
	}
	
	
} 
