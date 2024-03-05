package com.office.ticketreserve.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	final static public int ID_ALREADY_EXIST				= -2;
	final static public int DATABASE_COMMUNICATION_TROUBLE	= -1;
	final static public int INSERT_INFO_FAIL_AT_DATABASE	= 0;
	final static public int INSERT_INFO_OK_AT_DATABASE		= 1;
	

	public int createAccountConfirm(UserDto userDto) {
		log.info("createAccountConfirm");
		
		boolean isMember = userDao.isUser(userDto.getU_id());
		
		if (!isMember) {
			
			userDto.setU_pw(passwordEncoder.encode(userDto.getU_pw()));
			
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
        return !userDao.isUser(u_id_check);
    }




	public UserDto userLoginConfirm(UserDto userDto) {
		log.info("[UserService] userLoginConfirm()");
		
		UserDto dto =  userDao.selectUserForLogin(userDto);
		
		return dto;
	
		
	}





	public UserDto userModifyConfirm(UserDto userDto) {
		
		return null;
	}





	public int memberDeleteConfirm(int u_no) {
		
		return 0;
	}




	
}
