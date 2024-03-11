package com.office.ticketreserve.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class ReviewDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int insertReview(String rv_txt, int rv_score, String p_name, String u_id) {
		
		String sql = "INSERT INTO TBL_REVIEW VALUES("
				+ "REVIEW_SEQ.NEXTVAL,?,?,?,?,0,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, u_id, p_name,rv_score,rv_txt);
								
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return result;
	}

	public String getPname(String p_id) {
		
		String sql = "SELECT P_NAME FROM TBL_PERFOMANCE WHERE P_ID = ?";
		
		String p_name = null;
		
		try {
			p_name = jdbcTemplate.queryForObject(sql,String.class, p_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return p_name;
	}

	public List<ReviewDto> allSelectReviewByPname(String p_name) {
		
		String sql = "SELECT * FROM TBL_REVIEW WHERE P_NAME = ?";
		
		List<ReviewDto> reviewDtos = new ArrayList<>();
		try {
			RowMapper<ReviewDto> rowMapper =
					BeanPropertyRowMapper.newInstance(ReviewDto.class);
			
			reviewDtos = jdbcTemplate.query(sql,rowMapper, p_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewDtos;
	}
	
	
}
