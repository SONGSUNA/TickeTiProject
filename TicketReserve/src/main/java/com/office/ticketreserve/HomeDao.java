package com.office.ticketreserve;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.productpage.CurrentReserveDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class HomeDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<CurrentReserveDto> selectRankOnePerfos() {
		log.info("[HomeDao] selectRankOnePerfos()");
		
		String sql = "SELECT * FROM TBL_CURRENT_RESERVE WHERE C_R_RANK = 1";
        
		List<CurrentReserveDto> rankInfo = new ArrayList<>();
	    
	    try {
	    	RowMapper<CurrentReserveDto> rowMapper = 
					BeanPropertyRowMapper.newInstance(CurrentReserveDto.class);
	    	
	    	rankInfo = jdbcTemplate.query(sql, rowMapper);
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    log.info(rankInfo);
	    
		return rankInfo;
	}
	
}


