package com.office.ticketreserve.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.office.ticketreserve.admin.AdminDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {

	@Autowired
	UserService userService;
	
	// 유저 회원가입폼이동
	@GetMapping("/create_account_form")
	public String createAccountForm() {
		log.info("[UserController] createAccountForm()");
		
		String nextPage = "user/create_account_form";
		
		return nextPage;
	}
	
	
	// 유저 회원가입 확인
	@PostMapping("/create_account_confirm")
	public String createAccountConfirm(UserDto userDto,Model model) {
		log.info("[UserController] createAccountConfirm()");
		

		int result = userService.createAccountConfirm(userDto);
		model.addAttribute("rst", result);
		
		String nextPage = "/user/create_account_result";
		
		if (result <= UserService.INSERT_INFO_FAIL_AT_DATABASE) {
			log.info("INSERT_INFO_FAIL_AT_DATABASE");
		}
		
		return nextPage;
		
	}
	// 유저 아이디 중복체크버튼
	@PostMapping("/checkId")
	@ResponseBody
	public boolean checkId(@RequestParam("u_id_check") String u_id_check) {
	   log.info("[UserController] checkId()");
	   
		return userService.checkId(u_id_check);
	}
	
	// 유저 로그인폼 이동
	@GetMapping("/user_login_form")
	public String userLoginForm() {
		log.info("[UserController] userLoginForm()");
      
		String nextPage = "user/user_login_form";
      
		return nextPage;   
      
	}
	// 유저 로그인 확인
	@PostMapping("/user_login_confirm")
	public String userLoginConfirm(UserDto userDto, HttpSession session, Model model) {
		log.info("[UserController] userLoginConfirm()");
	      
		String nextPage = "/home";
	      
		Object loginedUserDto = userService.userLoginConfirm(userDto);
	    
		if (loginedUserDto instanceof UserDto) {
			
			session.setAttribute("loginedUserDto", loginedUserDto);
			session.setMaxInactiveInterval(60 * 30);
			
			return nextPage;
			
		} else if(loginedUserDto instanceof AdminDto){
			
			session.setAttribute("loginedAdminDto", loginedUserDto);
			
			return "redirect:/admin";
			
		} else 
			
			return "/user/user_login_ng";
		
	}
	// 유저 정보수정폼 이동
	@GetMapping("/user_modify_form")
	public String userModifyForm(Model model , HttpSession session) {
		log.info("[UserController] userModifyForm()");
		
		  String nextPage = "user/user_modify_form";
	      
		 UserDto loginedUserMemberDto = (UserDto) session.getAttribute("loginedUserDto");
		    
		    if (loginedUserMemberDto == null) {
		        return "redirect:/user_login_form"; 
		    }
		    
		    String scNumPre = loginedUserMemberDto.getU_sc_num().substring(0, 6);
		    String mailPre = loginedUserMemberDto.getU_mail().split("@")[0];
		    String mailSuf = loginedUserMemberDto.getU_mail().split("@")[1];
		    String centerPoneN = loginedUserMemberDto.getU_phone().split("-")[1];
		    String endPoneN = loginedUserMemberDto.getU_phone().split("-")[2];
		    String zipCode = loginedUserMemberDto.getU_address().split("/")[0];
		    String mainAdr = loginedUserMemberDto.getU_address().split("/")[1];
			/* String referenceAdr = loginedUserMemberDto.getU_address().split("//")[1]; */
		   		    
		    model.addAttribute("loginedUserMemberDto", loginedUserMemberDto);
		    model.addAttribute("scNumPre", scNumPre);
		    model.addAttribute("mailPre", mailPre);
		    model.addAttribute("mailSuf", mailSuf);
		    model.addAttribute("centerPoneN", centerPoneN);
		    model.addAttribute("endPoneN", endPoneN);
		    model.addAttribute("zipCode", zipCode);
		    model.addAttribute("mainAdr", mainAdr);
			/* model.addAttribute("referenceAdr", referenceAdr); */
		    
		    return nextPage;
	}
	//유저 정보수정 확인
	@PostMapping("/user_modify_confirm")
	public String userModifyConfirm(UserDto userDto, HttpSession session) {
	   log.info("[UserController] userModifyConfirm()");
	    
	   String nextPage = "user/user_modify_ok";
	   System.out.println("=========>" + userDto);
	   UserDto loginedUserDto = userService.userModifyConfirm(userDto);
	    
	    if (loginedUserDto != null) {
	       
	       session.setAttribute("loginedUserDto", loginedUserDto);
	       session.setMaxInactiveInterval(60 * 30);
	   
	    } else {
	       
	       nextPage = "user/user_modify_ng";
	       
	    }
	    
	    return nextPage;
	    
	}
	
	/*
	 * 유저 로그아웃
	 */
	@GetMapping("/user_logout_confirm")
    public String userLogoutConfirm(HttpSession session) {
		log.info("[UserController] userLogoutConfirm()");

		String nextPage = "redirect:/";
		
		session.removeAttribute("loginedUserDto");
		
		return nextPage;
		
    }
	/*
	 * 유저 회원탈퇴
	 */
	@GetMapping("/user_delete_confirm")
	public String userDeleteConfirm(HttpSession session) {
		log.info("[UserController] userDeleteConfirm()");
      
		String nextPage = "redirect:/user/user_logout_confirm";

		UserDto loginedUserDto =
				(UserDto) session.getAttribute("loginedUserDto");
		
		int result = userService.userDeleteConfirm(loginedUserDto.getU_no());
		if (result <= 0) {
	         nextPage = "user/user_delete_ng";
		} 
      
		return nextPage;
	}
	//아이디 찾기
	@GetMapping("/user_find_id_form")
	private String uFindIdFrom() {
		log.info("[UserController] uFindIdFrom()");
		
		return "user/user_find_password_form";
	}
	
	//	비밀번호 찾기
	@GetMapping("/user_find_password_form")
	private String	uFindPwform() {
		log.info("[UserController] uFindPwform()");
		
		return "user/user_find_password_form";

		 
	}
	
	// 비밀번호찾기 확인
	@PostMapping("/user_password_find")
	private String uFindPwConfirm(UserDto userDto) {
		log.info("[UserController] uFindPwConfirm()");
		
		String nextPage = "user/user_find_password_ok";
		
		int result = userService.uFindPwConfirm(userDto);
		
		if(result > 0) {
			return nextPage;
		}
			return "/user/user_find_password_ng";
		
	}
	
}
