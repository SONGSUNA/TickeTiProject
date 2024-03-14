package com.office.ticketreserve.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Repository
public class CategoryDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<PerfomanceDto> selectConcert(String category) {
		
		String sql ="SELECT * FROM TBL_PERFOMANCE WHERE P_CATEGORY = ?";
		
		List<PerfomanceDto> concertDtos = new ArrayList<>();
		try {
			RowMapper<PerfomanceDto> rowMapper = BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
			concertDtos = jdbcTemplate.query(sql, rowMapper, category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return concertDtos;
	}

	public List<CurrentReserveDto> selectRanking(String categoryNameForRanking) {
		
		String sql = "SELECT * FROM TBL_CURRENT_RESERVE WHERE P_CATEGORY = ?";
		
		List<CurrentReserveDto> rankingDtos = new ArrayList<>();
		try {
			RowMapper<CurrentReserveDto> rowMapper = BeanPropertyRowMapper.newInstance(CurrentReserveDto.class);
			rankingDtos = jdbcTemplate.query(sql, rowMapper,categoryNameForRanking);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rankingDtos;
	}

	
	  public List<PerfomanceDto> selectUpcoming(String categoryName) {
	  
	  String sql = "SELECT * FROM TBL_PERFOMANCE WHERE P_CATEGORY = ? AND P_START_DATE > SYSDATE ORDER BY P_START_DATE";
	  
	  List<PerfomanceDto> categoryUpcomingDtos = new ArrayList<>();
		try {
			RowMapper<PerfomanceDto> rowMapper = BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
			categoryUpcomingDtos = jdbcTemplate.query(sql, rowMapper,categoryName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categoryUpcomingDtos;
	  }

	public List<PerfomanceDto> selectNewPerfomance(String categoryName) {
		
		String sql = "SELECT * FROM TBL_PERFOMANCE WHERE P_CATEGORY = ? AND P_START_DATE < SYSDATE ORDER BY P_START_DATE DESC";
		  
		  List<PerfomanceDto> newPerfomanceDtos = new ArrayList<>();
			try {
				RowMapper<PerfomanceDto> rowMapper = BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
				newPerfomanceDtos = jdbcTemplate.query(sql, rowMapper,categoryName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return newPerfomanceDtos;
	}
	 

}
