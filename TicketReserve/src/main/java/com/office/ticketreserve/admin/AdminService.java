package com.office.ticketreserve.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.user.IUserDaoForMybatis;
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
	IUserDaoForMybatis userDao;

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

	public UserDto selectUserByID(String u_id) {
		log.info("[AdminService] selectUserByID()");
		
		return adminDao.selectUsersById(u_id).get(0);
	}

	public void userModifyConfirm(UserDto userDto) {
		log.info("[AdminService] userModifyConfirm()");
		
		adminDao.updateUserWithoutPw(userDto);
	}

	public boolean isAdmin(String adminId) {
		log.info("[AdminService] isAdmin()");
		
		boolean result = true;
		
		AdminDto adminDto = adminDao.selectAdminById(adminId);
		if (adminDto != null) return result;
		else return false;
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

	public AdminDto selectAdminById(String a_id) {
		log.info("[AdminService] selectAdminById()");
	
		return adminDao.selectAdminById(a_id);
	}

	public void adminModifyConfirm(AdminDto adminDto) {
		log.info("[AdminService] adminModifyConfirm()");
		
		adminDao.updateAdminWitoutPw(adminDto);
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
		log.info("[AdminService] perfomanceRegistConfirm()");
		
		if (perfomanceDto.getP_detail_cautions() != null)
			adminDao.insertPerfomance(perfomanceDto);
		else
			adminDao.insertPerfomanceNotDetailCautions(perfomanceDto);
	}

	public List<PerfomanceDto> getAllPerfomance() {
		log.info("[AdminService] getAllPerfomance()");
		
		return adminDao.selectAllPerfomance();
	}

	public List<PerfomanceDto> getNoTicketPfs() {
		log.info("[AdminService] getNoTicketPfs()");
		
		return adminDao.selectAllPerfomanceNoTicket();
	}

	public List<PerfomanceDto> getPerfomanceByName(String p_name) {
		log.info("[AdminService] getPerfomanceByName()");
		
		return adminDao.selectAllPerfomanceByName(p_name);
	}

}


