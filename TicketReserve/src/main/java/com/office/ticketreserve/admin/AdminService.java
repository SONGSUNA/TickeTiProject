package com.office.ticketreserve.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.user.UserDao;
import com.office.ticketreserve.user.UserDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {
	
	@Autowired
	AdminDaoForMyBatis adminDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<UserDto> getAllUserDto() {
		log.info("[AdminService] getAllUserDto()");
		
		List<UserDto> userDtos = adminDao.selectAllUsers();
		
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_no());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_id());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_pw());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_name());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_mail());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_phone());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_address());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_reg_date());
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userDtos.get(0).getU_mod_date());
		
		return userDtos;
	}

	public boolean checkId(String adminId) {
		log.info("[AdminService] checkId()");
		
		AdminDto adminDto = adminDao.selectAdminById(adminId);
		
		return adminDto == null ? true : false;
	}

	public int adminRegist(AdminDto adminDto) {
		log.info("[AdminService] adminRegist()");
		
		AdminDto checkAdminDto = adminDao.selectAdminById(adminDto.getA_id());
		if(checkAdminDto != null) {
			return -99;
		}
		
		String encodePw = passwordEncoder.encode(adminDto.getA_pw());
		adminDto.setA_pw(encodePw);
		
		return adminDao.insertAdmin(adminDto);
	}	

}


