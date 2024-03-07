package com.office.ticketreserve.user;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.admin.AdminDaoForMyBatis;
import com.office.ticketreserve.admin.AdminDto;

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
	
	
	
	final static public int ID_ALREADY_EXIST				= -2;
	final static public int DATABASE_COMMUNICATION_TROUBLE	= -1;
	final static public int INSERT_INFO_FAIL_AT_DATABASE	= 0;
	final static public int INSERT_INFO_OK_AT_DATABASE		= 1;
	

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
	public boolean checkId(String u_id_check) {
		log.info("[UserService] checkId()");
		
		boolean result = userDao.isUser(u_id_check); 
		
        return result;
    }




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





	public UserDto userModifyConfirm(UserDto userDto) {
		log.info("[UserService] userModifyConfirm()");
		
		int result =userDao.editUserInfo(userDto);
		
		if(result > 0)
			return userDao.getLatestUserInfo(userDto);
		else
			return null;
	}

	
	// 회원 탈퇴 확인
	public int userDeleteConfirm(int u_no) {
		log.info("[UserService] userLoginConfirm()");
		
		return IUserDao.deleteUser(u_no);
	}
	
	//비밀번호 찾기 확인
	public int uFindPwConfirm(UserDto userDto) {
		log.info("[UserService] userLoginConfirm()");
		
		UserDto userDtos = userDao.seletUserFindInfo(userDto.getU_id(), userDto.getU_mail());
		System.out.println("==========>>>>>>>>>>" + userDtos);
		
		int result = 0;
		
		if(userDtos != null) {
			
			String newPassword = createNewPassword();
			System.out.println("<<<<=============>>>> "+ newPassword );
			result = userDao.updatePassword(userDto.getU_id(), newPassword);
			
			if (result > 0)
				
				sendNewPasswordByMail(userDto.getU_mail(), newPassword);
			
		}
		
			return result;
	}
	
	
	//비밀번호 찾기 메일 전송하기
	private void sendNewPasswordByMail(String toMailAddr , String newPassword) {
		log.info("[UserService] sendNewPasswordByMail()");
		
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
	

	
}
