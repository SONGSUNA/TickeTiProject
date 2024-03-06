package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ProductController {
	@Autowired
	ProductService productService;
	
	@RequestMapping("/product/{pid}")
	public String productPage(@PathVariable("pid") String pId, Model model) {
		log.info("productPage()");
		
		PerfomanceDto productDto = productService.productPage(pId);
		model.addAttribute("productDto", productDto);
		
		
		String nextPage = "/product_page/product_page";
		
		return nextPage;
	}
}
