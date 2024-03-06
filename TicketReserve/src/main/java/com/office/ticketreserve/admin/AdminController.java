package com.office.ticketreserve.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.ticketreserve.user.UserDao;
import com.office.ticketreserve.user.UserDto;

import jakarta.servlet.http.HttpSession;
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
	public String user_management(Model model) {
		log.info("[AdminController] user_management()");
		
		List<UserDto> userDtos = adminService.getAllUserDto();
		
		model.addAttribute("userList", userDtos);
		
		String nextPage = "admin/user_management";
		
		return nextPage;
	}
	
	@GetMapping("/user_search")
	@ResponseBody
	public List<UserDto> user_search(@RequestParam("u_id") String u_id,
									 @RequestParam("u_name") String u_name,
									 @RequestParam("u_mail") String u_mail) {
		log.info("[AdminController] user_search()");
		
		List<UserDto> userDtos = adminService.getSelectUserDtos(u_id, u_name, u_mail);

		return userDtos;
	}
	
	@GetMapping("/admin_regist")
	public String admin_regist() {
		log.info("[AdminController] admin_regist()");
		
		String nextPage = "/admin/admin_regist";
			
		return nextPage;
	}
	
	@ResponseBody
	@PostMapping("/checkId")
	public boolean checkId(@RequestParam("a_id_check") String id) {
	    log.info("[AdminController] checkId()");
	    
	    boolean checked = false;
	    
	    checked = adminService.checkId(id);
	    
	    if (checked) {
	    	UserDao userDao = new UserDao();
	    	checked = userDao.isUser(id);
	    }
	    
	    return checked;
	}
	
	@PostMapping("/admin_regist_confirm")
	public String checkId(AdminDto adminDto) {
		log.info("[AdminController] admin_regist_confirm()");
		
		int result = adminService.adminRegist(adminDto);
		
		String nextPage = "/admin/regist_success";
	
		if (result <= 0)
			nextPage = "/admin/regist_fail";
		
		return nextPage;
	}
	
	@GetMapping("/admin_management")
	public String admin_management() {
		log.info("[AdminController] admin_management()");
		
		String nextPage = "/admin/admin_management";
		
		return nextPage;
		
	}
	
} 
