package com.office.ticketreserve.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isUser(String u_id) {
		log.info("[UserDao] isUser()");
		
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
							+ "U_SC_NUM,"
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
											userDto.getU_sc_num(),
											userDto.getU_address());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public UserDto selectUserForLogin(UserDto userDto) {
		log.info("[UserDao] selectUserForLogin()");
		log.info(userDto.getU_id() +","+ userDto.getU_pw());
		
		String sql = "SELECT * FROM TBL_USER WHERE U_ID = ?";
		
		List<UserDto> userDtos = new ArrayList<UserDto>();
        
		try {
			
			RowMapper<UserDto> rowMapper = 
					BeanPropertyRowMapper.newInstance(UserDto.class);
			userDtos = jdbcTemplate.query(sql, rowMapper, userDto.getU_id());
			
			if(userDtos.isEmpty()) {
				System.out.println("true");
			} else {
				System.out.println("false");
			}
			
			if(passwordEncoder.matches(userDto.getU_pw(), userDtos.get(0).getU_pw())) {
				
				System.out.println("true");
			} 
			else {
				System.out.println("false");
				System.out.println(userDtos.get(0).getU_pw());
				
				
				userDtos.clear();
			}
			
			/*
			 * if (userDtos.isEmpty() || !passwordEncoder.matches(userDto.getU_pw(),
			 * userDtos.get(0).getU_pw())) { userDtos.clear(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		System.out.println("====>" + userDtos);
		return userDtos.size() > 0 ? userDtos.get(0) : null;
		 
}
}
