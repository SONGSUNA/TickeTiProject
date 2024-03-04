package com.office.worldcup.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class AdminDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insertAdmin(AdminDto adminDto) {
		log.info("[AdminDao] insertAdmin()");
		
		String sql = "INSERT INTO "
					+ "TBL_ADMIN("
						+ "A_NO, "
						+ "A_ID, "
						+ "A_PW, "
						+ "A_NAME, "
						+ "A_MAIL, "
						+ "A_PHONE, "
						+ "A_REG_DATE, "
						+ "A_MOD_DATE) "
					+ "VALUES("
						+ "ADMIN_SEQ.NEXTVAL, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";
		
		int result = -1;
		
		try {
			result =
					jdbcTemplate.update
					(sql, adminDto.getA_id(), adminDto.getA_pw(), adminDto.getA_name(), adminDto.getA_mail(), adminDto.getA_phone());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}

}
