package com.office.ticketreserve.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import lombok.extern.log4j.Log4j2;
@Log4j2
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

	public ReviewDto getReviewByRv_no(int rv_no) {
		
		String sql = "SELECT * FROM TBL_REVIEW WHERE RV_NO = ?";
		ReviewDto reviewDto = null;
		
		try {
			RowMapper<ReviewDto> rowMapper= BeanPropertyRowMapper.newInstance(ReviewDto.class);
			reviewDto = jdbcTemplate.queryForObject(sql,rowMapper,rv_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewDto;
	}

	public int updateReviewByRv_no(String rv_txt, int rv_score, int rv_no) {
		
		String sql = "UPDATE TBL_REVIEW SET RV_TXT = ?,RV_SCORE = ?,RV_MOD_DATE = CURRENT_TIMESTAMP WHERE RV_NO =?";
		
		int result = -1;
		try {
			
			result = jdbcTemplate.update(sql,rv_txt,rv_score,rv_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int reviewDeleteConfirm(int rv_no) {
		log.info("reviewDeleteConfirm");
		String sql = "DELETE FROM TBL_REVIEW WHERE RV_NO = ?";
		
		int result = -1;
		try {
			result = jdbcTemplate.update(sql,rv_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public double getAllStarValue(String p_name) {
		
		String sql = "SELECT ROUND(AVG(RV_SCORE),1) FROM TBL_REVIEW WHERE P_NAME = ?";
		
		Double allStarAvg = null;
		
		try {
			allStarAvg = jdbcTemplate.queryForObject(sql,Double.class,p_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return allStarAvg !=null ? allStarAvg : 0.0;
	}

	
}
