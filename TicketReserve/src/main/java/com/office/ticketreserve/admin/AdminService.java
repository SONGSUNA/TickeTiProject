package com.office.ticketreserve.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.PerfomanceDto;
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
	
	@Autowired
	UserDao userDao;

	public List<UserDto> getAllUserDto() {
		log.info("[AdminService] getAllUserDto()");
		
		List<UserDto> userDtos = adminDao.selectAllUsers();
		
		return userDtos;
	}

	public List<UserDto> getSelectUserDtos(String u_id, String u_name, String u_mail) {
		log.info("[AdminService] getSelectUserDtos()");
		
		List<UserDto> userDtos = null;
		
		if(u_id != "") {
			userDtos = adminDao.selectUsersById(u_id);
			return userDtos;
		}
		else if (u_name != "") {
			userDtos = adminDao.selectUsersByName(u_name);
			return userDtos;
		}
		else
			userDtos = adminDao.selectUsersByMail(u_mail);
			return userDtos;
	}

	public boolean isAdmin(String adminId) {
		log.info("[AdminService] isAdmin()");
		
		boolean result = true;
		
		AdminDto adminDto = adminDao.selectAdminById(adminId);
		if (adminDto != null) return result;
		
		return userDao.isUser(adminId);
	}

	public int adminRegist(AdminDto adminDto) {
		log.info("[AdminService] adminRegist()");
		
		AdminDto checkAdminDto = adminDao.selectAdminById(adminDto.getA_id());
		if(checkAdminDto != null) return -99;
		
		String encodePw = passwordEncoder.encode(adminDto.getA_pw());
		adminDto.setA_pw(encodePw);
		
		return adminDao.insertAdmin(adminDto);
	}

	public List<AdminDto> getAllAdminDtos() {
		log.info("[AdminService] adminRegist()");
		
		return adminDao.selectAllAdmins();
	}

	public List<AdminDto> getSelectAdminDtos(String a_id, String a_name, String a_mail) {
		log.info("[AdminService] getSelectAdminDtos()");
		
		List<AdminDto> adminDtos = null;
		
		if(a_id != "") {
			adminDtos = adminDao.selectAdminsById(a_id);
			return adminDtos;
		}
		else if (a_name != "") {
			adminDtos = adminDao.selectAdminsByName(a_name);
			return adminDtos;
		}
		else
			adminDtos = adminDao.selectAdminsByMail(a_mail);
			return adminDtos;
	}

	public void adminDeleteConfirm(int a_no) {
		log.info("[AdminService] getSelectAdminDtos()");
		
		adminDao.deleteAdmin(a_no);
	}

	public boolean isPfId(String id) {
		log.info("[AdminService] isPfId()");
		
		boolean result = true;
		
		PerfomanceDto perfomanceDto = adminDao.selectPerfomanceById(id);
		if (perfomanceDto != null) return result;
		
		return userDao.isUser(id);
	}

	public void perfomanceRegistConfirm(PerfomanceDto perfomanceDto) {
		log.info("[AdminService] isPfId()");
		
		if (perfomanceDto.getP_detail_cautions() != null)
			adminDao.insertPerfomance(perfomanceDto);
		else
			adminDao.insertPerfomanceNotDetailCautions(perfomanceDto);
	}

}


