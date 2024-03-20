package com.office.ticketreserve.reservation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.ticketreserve.user.UserDto;

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
	public String step3(HttpSession session,
						@RequestParam("totalPrice") int totalPrice,
						@RequestParam("selectedSeats") String selectedSeats) {
		log.info("[ReservationController] step3()");
		
		String nextPage = "ticket_reservation/ticket_reservation_step3";
		
		String[] selectedSeatsArray = selectedSeats.split(",");
		List<String> selectedSeatsList = Arrays.asList(selectedSeatsArray);
		
		session.setAttribute("totalPrice", totalPrice);
		session.setAttribute("selectedSeats", selectedSeatsList);
		
		return nextPage;
	}
	
	@GetMapping({"/step4"})
	public String home(HttpSession session,
            @RequestParam("discount") int discount,
            @RequestParam("step3") String step3) {
		log.info("[ReservationController] step4()");
		
		session.setAttribute("discount", discount);
		session.setAttribute("step3", step3);
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
	    
	    UserDto userDto = (UserDto) session.getAttribute("loginedUserDto");
	    
	    return userDto == null ? "logout" : "login";
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
										 @RequestParam("selectedTimeInfo") String selectedTimeInfo,
										 HttpSession session) {
		log.info("[ReservationController] getReserveSeat()");
		
		
		session.setAttribute("t_no", t_no);
		return reservationService.getReserveSeat(t_no, selectedDate, selectedTimeInfo); 
	}
	
	@GetMapping("/getInfoForStep3")
	@ResponseBody
	public Map<String, Object> getInfoForStep3(HttpSession session) {
		log.info("[ReservationController] getInfoForStep3()");
		
		Map<String, Object> hashMap = new HashMap<>();
		
		hashMap.put("totalPrice", session.getAttribute("totalPrice"));
		hashMap.put("selectedSeats", session.getAttribute("selectedSeats"));
		hashMap.put("t_no", session.getAttribute("t_no"));
		UserDto userDto = (UserDto) session.getAttribute("loginedUserDto");
		hashMap.put("u_id", userDto.getU_id());
		
		return hashMap;
	}
	
	@GetMapping("/getInfoForStep4")
	@ResponseBody
	public Map<String, Object> getInfoForStep4(HttpSession session) {
	    log.info("[ReservationController] getInfoForStep4()");
	    
	    Map<String, Object> hashMap = new HashMap<>();
	    hashMap.put("totalPrice", session.getAttribute("totalPrice"));
		hashMap.put("selectedSeats", session.getAttribute("selectedSeats"));
	    hashMap.put("discount", session.getAttribute("discount"));
	    hashMap.put("step3", session.getAttribute("step3"));
	    
	    UserDto userDto = (UserDto) session.getAttribute("loginedUserDto");
	    hashMap.put("u_id", userDto.getU_id());
	    hashMap.put("u_mail", userDto.getU_mail());
	    hashMap.put("u_name", userDto.getU_name());
	    
	    return hashMap;
	}
	
	@PostMapping("/setTikietInfo")
	@ResponseBody
	public String setTikietInfo(@RequestBody HashMap<String, Object> tiketInfo,
								HttpSession session) {
		log.info("[ReservationController] setTikietInfo()");
		
		session.setAttribute("tiketInfo", tiketInfo);
		
		return "티켓 정보 저장";
	}
	
	@GetMapping("/getUserInfo")
	public UserDto getUserInfo(@RequestParam("p_id") String p_id,
								HttpSession session) {
		log.info("[ReservationController] getUserInfo()");

		return (UserDto) session.getAttribute("loginedUserDto");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/reserveConfirm")
	@ResponseBody
	public int reserveConfirm(HttpSession session) {
		log.info("[ReservationController] reserveConfirm()");
		
		Map<String, Object> hashMap =  (HashMap<String, Object>) session.getAttribute("tiketInfo");
		
		return reservationService.reserveConfirm(hashMap);
	}
	
	@GetMapping("/cancel_confirm")
	public String cancelConfirm(@RequestParam("r_no") int r_no,
								@RequestParam("t_no") int t_no) {
		log.info("[ReservationController] cancel_confirm()");
		
		reservationService.cancelConfirm(r_no, t_no);
		
		return "redirect:/user/my_ticket_page";
	}
} 
