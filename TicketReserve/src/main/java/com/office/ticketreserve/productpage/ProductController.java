package com.office.ticketreserve.productpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.ticketreserve.review.ReviewDto;
import com.office.ticketreserve.review.ReviewService;
import com.office.ticketreserve.user.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ReviewService reviewService;
	
	
//	@RequestMapping("/product/{p_id}")
//	public String productPage(@PathVariable("p_id") String p_id, Model model) {
//		log.info("productPage()");
//		
//		
//		PerfomanceDto productDto = productService.productPage(p_id);
//
//		List<ReviewDto> reviewDtos = reviewService.allReviewsForPid(p_id);
//		
//		model.addAttribute("productDto", productDto);
//		model.addAttribute("reviewDtos", reviewDtos);
//		
//		
//		
//		String nextPage = "/product_page/product_page";
//		
//		return nextPage;
//	}
	@RequestMapping("/product/{p_id}")
	public String productPage(@PathVariable("p_id") String p_id, Model model, HttpSession session) {
	    log.info("productPage()");
	    PerfomanceDto productDto = productService.productPage(p_id);
	    List<ReviewDto> reviewDtos = reviewService.allReviewsForPid(p_id);

	    UserDto loginedUserDto = (UserDto) session.getAttribute("loginedUserDto");
	    boolean isLiked = false;
	    if (loginedUserDto != null) {
	        isLiked = productService.isLiked(p_id, loginedUserDto.getU_id());
	    }

	    model.addAttribute("productDto", productDto);
	    model.addAttribute("reviewDtos", reviewDtos);
	    model.addAttribute("isLiked", isLiked);
	    model.addAttribute("isLogined", loginedUserDto != null);

	    String nextPage = "/product_page/product_page_test";
	    return nextPage;
	}
	@ResponseBody
	@PostMapping("/product/like")
	public int productLike(@RequestParam("p_id") String p_id,
	                       @RequestParam("u_id") String u_id) {
	    log.info("좋아요 토글");
	    return productService.toggleLike(p_id, u_id);
	}
	
	
}
