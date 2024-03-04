package com.office.worldcup.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;

	@GetMapping({"/", ""})
	public String admin_home() {
		log.info("[AdminController] admin_home()");
		
		String nextPage = "/admin/home";
		
		return nextPage;
	}
	
	@GetMapping("/user_management")
	public String user_management() {
		log.info("[AdminController] user_management()");
		
		String nextPage = "/admin/user_management";
		
		return nextPage;
	}
	
	@GetMapping("/admin_regist")
	public String admin_regist() {
		log.info("[AdminController] admin_regist()");
		
		String nextPage = "/admin/admin_regist";
			
		return nextPage;
	}
	
	@ResponseBody
	@PostMapping("/admin/checkId")
	public boolean checkId(String adminId) {
		log.info("[AdminController] checkId()");
		
		return adminService.checkId(adminId);
	}
	
	@GetMapping("/admin_management")
	public String admin_management() {
		log.info("[AdminController] admin_management()");
		
		String nextPage = "/admin/admin_management";
		
		return nextPage;
	}
	
}
