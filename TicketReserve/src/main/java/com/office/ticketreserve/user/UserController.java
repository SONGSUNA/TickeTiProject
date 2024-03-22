package com.office.ticketreserve.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String createAccountConfirm(UserDto userDto, Model model) {
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

	      Object loginedUserDto = userService.userLoginConfirm(userDto);

	      if (loginedUserDto instanceof UserDto) {

	         session.setAttribute("loginedUserDto", loginedUserDto);
	         session.setMaxInactiveInterval(60 * 30);

	         return "redirect:/";

	      } else if (loginedUserDto instanceof AdminDto) {

	         session.setAttribute("loginedAdminDto", loginedUserDto);

	         return "redirect:/admin";

	      } else

	         return "/user/user_login_ng";

	   }

	// 유저 정보수정폼 이동
	@GetMapping("/user_modify_form")
	public String userModifyForm(Model model, HttpSession session) {
		log.info("[UserController] userModifyForm()");

		String nextPage = "user/user_modify_form";

		UserDto loginedMemberDto = sessionCheck(session);

		if (loginedMemberDto == null) {
			return "redirect:/user_login_form";
		}

		String scNumPre = loginedMemberDto.getU_sc_num().substring(0, 6);
		String mailPre = loginedMemberDto.getU_mail().split("@")[0];
		String mailSuf = loginedMemberDto.getU_mail().split("@")[1];
		String centerPoneN = loginedMemberDto.getU_phone().split("-")[1];
		String endPoneN = loginedMemberDto.getU_phone().split("-")[2];
		String zipCode = loginedMemberDto.getU_address().split("/")[0];
		String mainAdr = loginedMemberDto.getU_address().split("/")[1];
		String detailAdr = loginedMemberDto.getU_address().split("/")[2];
		String extraAdr = loginedMemberDto.getU_address().split("/")[3];

		model.addAttribute("loginedMemberDto", loginedMemberDto);
		model.addAttribute("scNumPre", scNumPre);
		model.addAttribute("mailPre", mailPre);
		model.addAttribute("mailSuf", mailSuf);
		model.addAttribute("centerPoneN", centerPoneN);
		model.addAttribute("endPoneN", endPoneN);
		model.addAttribute("zipCode", zipCode);
		model.addAttribute("mainAdr", mainAdr);
		model.addAttribute("detailAdr", detailAdr);
		model.addAttribute("extraAdr", extraAdr);

		return nextPage;
	}

	// 유저 정보수정 확인
	@PostMapping("/user_modify_confirm")
	public String userModifyConfirm(UserDto userDto, HttpSession session) {
		log.info("[UserController] userModifyConfirm()");

		String nextPage = "user/user_modify_ok";
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
	
	//회원탈퇴 화면
	@GetMapping("/user_delete_form")
	public String userDeleteform() {
		log.info("[UserController] userDeleteform()");
		
		return  "user/user_delete_form";
		
	}
	
	//회원 탈퇴 확인
	@PostMapping("/user_delete_confirm")
	public String userDeleteConfirm(HttpSession session,UserDto userDto) {
		log.info("[UserController] userDeleteConfirm()");
		
		String nextPage = "redirect:/user/user_logout_confirm";

		UserDto loginedUserDto = sessionCheck(session);

		int result = userService.userDeleteConfirm(loginedUserDto.getU_no(),userDto);
		if (result <= 0) {
			nextPage = "user/user_delete_ng"; //수정하기 
		}
		
		return nextPage;
	}

	
	// 아이디 찾기
	@GetMapping("/user_find_id_form")
	private String uFindIdFrom() {
		log.info("[UserController] uFindIdFrom()");

		return "user/user_find_id_form";
	}

	
	// 아이디 찾기 확인
	@PostMapping("/doFindId")
	public String doFindId(UserDto userDto, Model model) {

		String userId = userService.dofindId(userDto, model);
		if (userId != null) {
			return "user/user_find_id_ok";
		} else {
			return "user/user_find_id_ng";

		}
	}

	// 비밀번호 찾기
	@GetMapping("/user_find_password_form")
	public String uFindPwform() {
		log.info("[UserController] uFindPwform()");

		return "user/user_find_password_form";

	}

	// 비밀번호찾기 확인
	@PostMapping("/user_password_find")
	public String uFindPwConfirm(UserDto userDto) {
		log.info("[UserController] uFindPwConfirm()");

		String nextPage = "user/user_find_password_ok";

		int result = userService.uFindPwConfirm(userDto);

		if (result <= 0) {
			return "/user/user_find_password_ng";
		}
		return nextPage;

	}

	// 상단 우측 사람 이미지 클릭 시 세션에 따른 화면 이동
	@GetMapping("/myPage")
	public String myPage(HttpSession session, Model model) {
		log.info("[UserController] myPage()");
		
		String nextPage = "user/user_login_form";
		
		UserDto userDtos = sessionCheck(session);
		if(userDtos == null) {
			return nextPage;
		}
			return myTicketHome(session,model);
					/*userModifyForm(model, session);*/
		
	}
	
	//세션확인 
	public UserDto sessionCheck(HttpSession session) {
		log.info("[UserController] sessionChek()");
		
		UserDto loginedUserDto = (UserDto) session.getAttribute("loginedUserDto");
		
		if(loginedUserDto != null) {
			return loginedUserDto;
		}
			return null;
		
	}
	
	//비밀번호 변경
	@GetMapping("/modify_pw_form")
	public String modifyFwFrom(HttpSession session) {
		log.info("[UserController] modifyFwFrom()");
		
		String nextPage = "user/modify_pw_form";
		UserDto userDtos =sessionCheck(session);
		
		if(userDtos == null) {
			return "user/user_find_password_ng";
		} 
			return nextPage;
	}
	
	//비밀번호 변경 확인
	@PostMapping("/user_modify_fw_confirm")
	public String modifyFwConfirm(UserDto userDto,HttpSession session,@RequestParam("new_pw") String newPassword) {
		log.info("[UserController] modifyFwConfirm()");
		UserDto loginedUserDto =sessionCheck(session);
		String nextPage = "user/user_modify_ok";
		
		int result = userService.modifyFwConfirm(newPassword,loginedUserDto,userDto);
		
		if(result <= 0) {
			nextPage = "user/modify_pw_ng";
		}
		
		return nextPage;
		
	}
	
	//나의 티켓 화면 이동
	@GetMapping("/my_ticket_page")
	public String myTicketHome(HttpSession session, Model model) {
	    log.info("[UserController] myTicketHome()");
	    
	    String nextPage = "user/my_ticket_page";
	    UserDto userDto = sessionCheck(session);
	    String u_id = userDto.getU_id();
	    
	    Map<String, Object> combinedDto = userService.getMyTicketInfo(u_id);
	    
	    if (combinedDto != null && !combinedDto.isEmpty()) {
	        model.addAttribute("combinedDto", combinedDto);
	    } else {
	        model.addAttribute("noTicketsMessage", "예매 내역이 없습니다.");
	    }
	    
	    Map<String, Object> combinedforReview = userService.getMyReview(u_id);
	    if (combinedforReview != null) { // null 체크 추가
	        if (!combinedforReview.isEmpty()) {
	            model.addAttribute("combinedforReview", combinedforReview);
	        } else {
	            model.addAttribute("noReviewMessage", "리뷰 내역이 없습니다.");
	        }
	    } else {
	        model.addAttribute("noReviewMessage", "리뷰 내역이 없습니다.");
	    }
	    
	    return nextPage;
	}
	
	//나의 리뷰 화면 이동

}
