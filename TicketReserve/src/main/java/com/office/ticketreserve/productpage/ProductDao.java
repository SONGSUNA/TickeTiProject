package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public PerfomanceDto selectProduct(String p_id) {
		
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

}
