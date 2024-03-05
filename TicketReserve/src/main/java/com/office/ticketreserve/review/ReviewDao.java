package com.office.ticketreserve.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ReviewDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int insertReview(String rv_txt, int rv_score) {
		
		String sql = "INSERT INTO TBL_DUMMY_REVIEW VALUES(?,?)";
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, rv_txt, rv_score);
								
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
	}

}
