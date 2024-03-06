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
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.queryForObject(sql,Integer.class,u_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result > 0 ? true : false;
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
			System.out.println("----------------->" + userDtos);

			if (userDtos.size() <= 0) {
				System.out.println("11111111111111111");
				return null;
				
			} else {
				
				if (!passwordEncoder.matches(userDto.getU_pw(), userDtos.get(0).getU_pw())) {
					System.out.println("33333333333333333333333");
					return null;
				} 
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return userDtos.get(0);
		 
	}

	public List<UserDto> selectAllUsers() {
		log.info("[UserDao] selectAllUsers()");
		
		String sql = "SELECT * FROM TBL_USER";
		
		List<UserDto> userDtos = new ArrayList<UserDto>();
        
		try {
			RowMapper<UserDto> rowMapper = 
					BeanPropertyRowMapper.newInstance(UserDto.class);
			userDtos = jdbcTemplate.query(sql, rowMapper);

			if (userDtos.isEmpty()) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return userDtos;
	}

	public static UserDto selectAdminById(String u_id_check) {
		log.info("[UserDao] selectAdminById()");
		
		return null;
	}

	public int deleteUser(int u_no) {
		log.info("[UserDao] deleteUser()");
		
		String sql =  "DELETE FROM TBL_USER "
				+ "WHERE U_NO = ?";
	
	int result = -1;
	
	try {
		
		result = jdbcTemplate.update(sql, u_no);
		
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return result;
	}

	public int editUserInfo(UserDto userDto) {
		log.info("[UserDao] deleteUser()");

		String sql =  "UPDATE "
				+ 	"TBL_USER"
				+ "SET "
				+ "U_PW, "
				+ "U_NAME, "
				+ "U_MAIL, "
				+ "U_PHONE, "
				+ "U_SC_NUM, "
				+ "U_ADDRESS, "
				+ "U_MOD_DATE = NOW() "
				+ "WHERE "
				+ 	"U_NO = ?";
	
	int result = -1;
	
	try {
		
		result = jdbcTemplate.update(sql, 
										userDto.getU_pw(),
										userDto.getU_name(),
										userDto.getU_mail(),
										userDto.getU_phone(),
										userDto.getU_sc_num(),
										userDto.getU_address(),
										userDto.getU_mod_date());
				
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return result;
	
	}

	public UserDto getLatestUserInfo(UserDto userDto) {
		log.info("[UserDao] getLatestUserInfo()");
		
		String sql = "SELECT * FROM TBL_USER WHERE U_NO = ?";
		List<UserDto> UserDtos = new ArrayList<>();
		
		try {
			
			RowMapper<UserDto> rowMapper =
					BeanPropertyRowMapper.newInstance(UserDto.class);
			
			UserDtos = jdbcTemplate.query(sql, rowMapper, userDto.getU_no());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return UserDtos.size() > 0 ? UserDtos.get(0) : null;	}

	public static UserDto selectUserById(String u_id_check) {
		log.info("[UserDao] getLatestUserInfo()");
		
		return null;
	}


}
