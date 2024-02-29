package com.office.worldcup.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isUser(String u_id) {
		
		String sql= "SELECT COUNT(*) FROM TBL_USER WHERE U_ID = ? ";
		
		
		try {
			
			int result = jdbcTemplate.queryForObject(sql,Integer.class,u_id);
			
			if (result >0) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	public int insertUser(UserDto userDto) {
		
		String sql= "INSERT INTO "
					+ "TBL_USER("
							+ "U_NO,"
							+ "U_ID,"
							+ "U_PW,"
							+ "U_NAME,"
							+ "U_MAIL,"
							+ "U_PHONE,"
							+ "U_REG_NUM,"
							+ "U_ADDRESS,"
							+ "U_REG_DATE,"
							+ "U_MOD_DATE) "
					+ "VALUES ("
							+ "USER_SEQ.NEXTVAL,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
		int result =-1;
		try {
			result= jdbcTemplate.update(sql,
											userDto.getU_id(),
											passwordEncoder.encode(userDto.getU_pw()),
											userDto.getU_name(),
											userDto.getU_mail(),
											userDto.getU_phone(),
											userDto.getU_reg_num(),
											userDto.getU_address());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
