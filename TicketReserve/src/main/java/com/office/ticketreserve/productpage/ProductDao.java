package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Repository
public class ProductDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public PerfomanceDto selectProduct(String p_id) {
		log.info("selectProduct");
		String sql ="SELECT * FROM TBL_PERFOMANCE WHERE P_ID = ?";
		
		
		PerfomanceDto productDto = new PerfomanceDto();
		try {
			RowMapper<PerfomanceDto> rowMapper = BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
			productDto = jdbcTemplate.queryForObject(sql, rowMapper,p_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productDto;
		
	
	}

	public int insertLike(String p_id, String u_id) {
		log.info("insertLike");
		
		String sql1= "INSERT INTO TBL_LIKE VALUES("
				+ "LIKE_SEQ.NEXTVAL,?,?,CURRENT_TIMESTAMP)";
		
		String sql2 = "UPDATE TBL_PERFOMANCE SET P_LIKE = P_LIKE + 1 WHERE P_ID = ?";
		int result = -1;
		try {
	        
	        result = jdbcTemplate.update(sql1, p_id, u_id);

	        if (result > 0) {
	            
	            jdbcTemplate.update(sql2, p_id);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
		return result;
	}
	
	public boolean selectIsLiked(String p_id, String u_id) {
	    log.info("selectIsLiked");

	    String sql = "SELECT COUNT(*) FROM TBL_LIKE WHERE P_ID = ? AND U_ID = ?";

	    int count = 0;
	    try {
	        count = jdbcTemplate.queryForObject(sql, Integer.class, p_id, u_id);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return count > 0;
	}
	
	public int deleteLike(String p_id, String u_id) {
	    log.info("deleteLike");

	    String sql1 = "DELETE FROM TBL_LIKE WHERE P_ID = ? AND U_ID = ?";
	    String sql2 = "UPDATE TBL_PERFOMANCE SET P_LIKE = P_LIKE - 1 WHERE P_ID = ?";

	    int result = -1;

	    try {
	        result = jdbcTemplate.update(sql1, p_id, u_id);
	        if (result > 0) {
	            jdbcTemplate.update(sql2, p_id);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
}
