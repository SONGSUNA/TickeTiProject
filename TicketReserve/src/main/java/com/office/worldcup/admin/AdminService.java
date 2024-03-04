package com.office.worldcup.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdminService {
	
	@Autowired
	AdminDao adminDao;

	public boolean checkId(String adminId) {
		log.info("[AdminService] checkId()");
		
		AdminDto adminDto = adminDao.selectAdminById(adminId);
		
		return adminDto == null ? true : false;
	}	

}
