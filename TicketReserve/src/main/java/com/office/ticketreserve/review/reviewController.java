package com.office.ticketreserve.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/review")
@Log4j2
public class reviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@GetMapping("")
	public String reviewAndGrade() {
		log.info("reviewAndGrade");
		
		String nextPage = "review/review";
		
		return nextPage;
	}
	
	
	@PostMapping("/review_write")
	public String reviewWrite(@RequestParam("rv_txt") String rv_txt, @RequestParam("rv_score") int rv_score, Model model) {
		log.info("review_write");
		
		int result = reviewService.reviewWrite(rv_txt,rv_score);
		model.addAttribute("rst", result);
		
		String nextPage = "review/review_result";
		
		return nextPage;
	}
}
