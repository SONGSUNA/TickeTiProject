package com.office.ticketreserve.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.user.UserDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/review/")
@Log4j2
public class reviewController {
	
	@Autowired
	ReviewService reviewService;
	

	
	@PostMapping("/review_write")
	public String reviewWrite(@RequestParam("rv_txt") String rv_txt, @RequestParam("rv_score") int rv_score,@RequestParam("p_id") String p_id, Model model,HttpSession session) {
		log.info("review_write");
		UserDto loginedUserDto=(UserDto) session.getAttribute("loginedUserDto");
		
		String u_id = loginedUserDto.getU_id();
		

		int result = reviewService.reviewWrite(rv_txt,rv_score,p_id,u_id);
		
		
		model.addAttribute("rst", result);
		
		String nextPage = "review/review_result";
		
		return nextPage;
	}
}
