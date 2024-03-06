package com.office.ticketreserve.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.productpage.PerfomanceDto;

@Repository
public class CategoryDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<PerfomanceDto> selectConcert() {
		
		String sql ="SELECT * FROM TBL_PERFOMANCE WHERE P_CATEGORY = '대중음악'";
		
		List<PerfomanceDto> concertDtos = new ArrayList<>();
		try {
			RowMapper<PerfomanceDto> rowMapper = BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
			concertDtos = jdbcTemplate.query(sql, rowMapper);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return concertDtos;
	}

}
