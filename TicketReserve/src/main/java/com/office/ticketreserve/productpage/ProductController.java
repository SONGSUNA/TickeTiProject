package com.office.ticketreserve.productpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/product_page")
@Log4j2
public class ProductController {
	
	@GetMapping("")
	public String productPage() {
		log.info("productPage()");
		
		String nextPage = "/product_page/product_page";
		
		return nextPage;
	}
}
