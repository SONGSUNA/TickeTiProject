package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.office.ticketreserve.user.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ProductController {
	@Autowired
	ProductService productService;
	
	@RequestMapping("/product/{p_id}")
	public String productPage(@PathVariable("p_id") String p_id, Model model,HttpSession session) {
		log.info("productPage()");
		
		UserDto loginedUserDto =(UserDto) session.getAttribute("loginedUserDto");
		
		
		PerfomanceDto productDto = productService.productPage(p_id);

		
		
		model.addAttribute("productDto", productDto);
		
		
		String nextPage = "/product_page/product_page";
		
		return nextPage;
	}
	

}
