package com.office.worldcup.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean isUser(String u_id) {
		
		return false;
	}

	public int insertUser(UserDao userDao) {
		
		return 0;
	}
}
