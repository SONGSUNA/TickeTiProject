package com.office.ticketreserve.user;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.office.ticketreserve.admin.AdminDaoForMyBatis;
import com.office.ticketreserve.admin.AdminDto;
import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.reservation.ReservationDto;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {
	@Autowired
	IUserDaoForMybatis IUserDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AdminDaoForMyBatis adminDaoForMyBatis;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	@Autowired
	ReservationDto reservationDto;
	
	@Autowired
	PerfomanceDto perfomanceDto;
	
	final static public int ID_ALREADY_EXIST				= -2;
	final static public int DATABASE_COMMUNICATION_TROUBLE	= -1;
	final static public int INSERT_INFO_FAIL_AT_DATABASE	= 0;
	final static public int INSERT_INFO_OK_AT_DATABASE		= 1;
	
	
	// 회원가입 확인하기
	public int createAccountConfirm(UserDto userDto) {
		log.info("createAccountConfirm");
		
		boolean isMember = userDao.isUser(userDto.getU_id());
		
		if (!isMember) {
			
//			userDto.setU_pw(passwordEncoder.encode(userDto.getU_pw()));
			
			int result = userDao.insertUser(userDto);
			
			if (result == DATABASE_COMMUNICATION_TROUBLE) {
				log.info("DATABASE COMMUNICATION TROUBLE");
			} else if (result == INSERT_INFO_FAIL_AT_DATABASE) {
				log.info("INSERT INFO FAIL AT DATABASE");
			} else if (result == INSERT_INFO_OK_AT_DATABASE) {
				log.info("INSERT INFO OK AT DATABASE");
			}
				
			return result;
		} else {
			return ID_ALREADY_EXIST;
		}
		
	}
	
	//아이디 중복확인
	public boolean checkId(String u_id_check) {
		log.info("[UserService] checkId()");
		
		boolean result = userDao.isUser(u_id_check); 
		
        return result;
    }



	//유저와 관리자 로그인 확인
	public Object userLoginConfirm(UserDto userDto) {
	    log.info("[UserService] userLoginConfirm()");
	    
	    UserDto seletedUserDto= userDao.selectUserForLogin(userDto);
	    
	    if(seletedUserDto != null) {
	        return seletedUserDto;
	    } 

	    AdminDto adminDto = adminDaoForMyBatis.selectAdminById(userDto.getU_id());
	    
	    if(adminDto != null && passwordEncoder.matches(userDto.getU_pw(), adminDto.getA_pw())) {
	    	return adminDto;
	    }
	        
	    return null;
	}


	// 정보수정 확인
	public UserDto userModifyConfirm(UserDto userDto) {
		log.info("[UserService] userModifyConfirm()");
		
		int result =userDao.editUserInfo(userDto);
		
		if(result > 0)
			return userDao.getLatestUserInfo(userDto);
		else
			return null;
	}

	
	// 회원 탈퇴 확인
	public int userDeleteConfirm(int u_no,UserDto userDto) {
		log.info("[UserService] userLoginConfirm()");
		String u_pw = userDto.getU_pw();
		int result = -1;
		
		UserDto userDtos = IUserDao.selectUser(u_no);
		if(userDtos != null && 
				passwordEncoder.matches(u_pw, userDtos.getU_pw())) {	//사용자입력값과 DB 비교
			
			result = IUserDao.deleteUser(u_no);
			
			if(result <= 0) {
				result = -1;
			} else {
				result = 1;
			}
		}
		
		 return result;
	}
	
	
	//비밀번호 찾기 확인
	public int uFindPwConfirm(UserDto userDto) {
		log.info("[UserService] userLoginConfirm()");
		
		UserDto userDtos = userDao.seletUserFindInfo(userDto.getU_id(), userDto.getU_mail());
		
		int result = 0;
		
		if(userDtos != null) {
			
			String newPassword = createNewPassword();
			
			result = userDao.updatePassword(userDto.getU_id(), newPassword);
			
			if (result > 0)
				
				sendNewPasswordByMail(userDto.getU_mail(), newPassword);
			
		}
		
			return result;
	}
	
	
	//비밀번호 찾기 메일 전송하기
	private void sendNewPasswordByMail(String toMailAddr , String newPassword) {
		log.info("[UserService] sendNewPasswordByMail()");
		
		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
				mimeMessageHelper.setTo("snsong91@gmail.com");
				mimeMessageHelper.setSubject("[TikeTi] 새 비밀번호 안내입니다.");
				String emailBody = "새 비밀번호: " + newPassword + "<br><br>"
                        + "<p style='color:red;'>로그인 후 보안을 위해 비밀번호를 변경해주세요.</p>";
				mimeMessageHelper.setText(emailBody, false);
			}
		};
		
		javaMailSenderImpl.send(mimeMessagePreparator);
		return;
    }
		

	// 새로운 비밀번호 생성
	private String createNewPassword() {
		log.info("[UserService] createNewPassword()");
		
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z'
				};
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 6; i++) {
			index = secureRandom.nextInt(length);
			
			if (index % 2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			
		}
		
		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());
		
		return stringBuffer.toString();
		
	}
	
	//아이디 찾기 
	public String dofindId(UserDto userDto, Model model) {
		log.info("[UserService] dofindId()");
		
		  String userId = userDao.dofindIdFromDB(userDto.getU_name(), userDto.getU_mail());
		    
		    if (userId != null) {
		        model.addAttribute("userId", userId);
		    }
		   
		    return userId;
	}
	
	// 비밀번호 변경하기 
	public int modifyFwConfirm(String newPassword, UserDto loginedUserDto, UserDto userDto) {
		log.info("[UserService] modifyFwConfirm()");
		
		int u_no = loginedUserDto.getU_no();
		String u_id = loginedUserDto.getU_id();
		String current_pw = userDto.getU_pw();
		int result = -1;
		UserDto userDtos = IUserDao.selectUser(u_no);
		
		if(userDtos != null && 
				passwordEncoder.matches(current_pw, userDtos.getU_pw())) {	//사용자입력값과 DB 비교
//			String u_pw = passwordEncoder.encode(newPassword);
			result = userDao.updatePassword(u_id, newPassword);
			
			if(result > 0) {
				result = 1;
			} 
		}
		return result;
	
	}

	public Map<String, Object> getMyTicketInfo(String u_id) {
		log.info("[UserService] getMyTicketInfo()");
		
		Map<String, Object> combinedInfo = new HashMap<>();
		List<ReservationDto> myReservationDto  = new ArrayList<>();
		
		myReservationDto = IUserDao.getMyTicketinfo(u_id);
		
		// t_no 뽑아내기
		List<String> r_reg_dateColection = new ArrayList<>();
		List<String> t_seatColection = new ArrayList<>();
		List<String> r_dateColection = new ArrayList<>();
		List<String> r_timeColection = new ArrayList<>();
		List<String> r_take_ticketColection = new ArrayList<>();
		List<String> r_payment_stateColection = new ArrayList<>();
		List<String> ticketNumbers = new ArrayList<>();
		for(ReservationDto reservationDto : myReservationDto) {
			ticketNumbers.add(Integer.toString(reservationDto.getT_no()));
			r_reg_dateColection.add(reservationDto.getR_reg_date()); 
			t_seatColection.add(reservationDto.getT_seat());
			r_dateColection.add(reservationDto.getR_date());
			r_timeColection.add(reservationDto.getR_time());
			r_take_ticketColection.add(Integer.toString(reservationDto.getR_take_ticket()));
			r_payment_stateColection.add(Integer.toString(reservationDto.getR_payment_state()));
		}
		
		//p_id 얻어오기
		List<String> myPIdsList = new ArrayList<>();
		 for (String tNo :ticketNumbers) { 
			 
			List<String> myPIds = IUserDao.getPerfomanceId(tNo); 
			 System.out.println("myPIds---->>"+ myPIds);
			 myPIdsList.addAll(myPIds);
			 }
		 System.out.println("myPIdsList====>>" + myPIdsList);
		 
		 //perfomanceDto 얻어오기
		 List<PerfomanceDto> myPerfomanceDto = new ArrayList<>();
		 List<String> p_nameColection = new ArrayList<>();
		 List<String> p_thumColectiion = new ArrayList<>();
		 for(String p_id : myPIdsList) {
			 
			 myPerfomanceDto = IUserDao.getPerfomaceInfo(p_id);
			 for(PerfomanceDto perfomanceDto : myPerfomanceDto) {
				 p_nameColection.add(perfomanceDto.getP_name());
				 p_thumColectiion.add(perfomanceDto.getP_thum());
			 }
		 }
		System.out.println("myPerfomanceDto===>>>>>"+myPerfomanceDto);
		combinedInfo.put("r_reg_dateColection", r_reg_dateColection);
		combinedInfo.put("t_seatColection", t_seatColection);
		combinedInfo.put("r_dateColection", r_dateColection);
		combinedInfo.put("r_timeColection", r_timeColection);
		combinedInfo.put("r_take_ticketColection", r_take_ticketColection);
		combinedInfo.put("r_payment_stateColection", r_payment_stateColection);
		combinedInfo.put("p_nameColection", p_nameColection);
		combinedInfo.put("p_thumColectiion", p_thumColectiion);
		combinedInfo.put("myPIdsList", myPIdsList);
		
		return combinedInfo;
	}
}
