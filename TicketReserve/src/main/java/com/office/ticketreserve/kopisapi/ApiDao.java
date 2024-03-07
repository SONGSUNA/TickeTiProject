package com.office.ticketreserve.kopisapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.user.UserDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ApiDao {
    
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	PerfomanceDto perfomanceDto;

    public int insertPerfomance(PerfomanceDto dto) {
        log.info("insertPerfomance() : " + dto.getPId());
        String sql = "INSERT INTO TBL_PERFOMANCE ("
                    + "P_ID, "
                    + "P_NAME, "
                    + "P_START_DATE, "
                    + "P_END_DATE, "
                    + "P_GRADE, "
                    + "P_THEATER, "
                    + "P_PLACE, "
                    + "P_THUM, "
                    + "P_CATEGORY, "
                    + "P_MAX_RESERVE, "
                    + "P_NOW_RESERVE, "
                    + "P_RUNNING_TIME, "
                    + "P_CHARACTERS, "
                    + "P_TICKET, "
                    + "P_LIKE, "
                    + "P_DETAIL_CAUTIONS, "
                    + "P_DETAIL_IMG, "
                    + "P_AGENCY_INFO, "
                    + "P_HOST, "
                    + "P_INQUIRY, "
                    + "P_REG_DATE, "
                    + "P_MOD_DATE) "
                    + "VALUES ("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP"
                    + ")";
        int result = -1;
        
        try {
            result = jdbcTemplate.update(sql, 
            			dto.getPId(), 
            			dto.getPName(), 
            			dto.getPStartDate(), 
            			dto.getPEndDate(),
            			dto.getPGrade(),
                        dto.getPTheater(),
                        dto.getPPlace(),
                        dto.getPThum(),
                        dto.getPCategory(),
                        dto.getPMaxReserve(),
                        dto.getPNowReserve(),
                        dto.getPRunningTime(),
                        dto.getPCharacters(),
                        dto.getPTicket(),
                        dto.getPLike(),
                        dto.getPDetailCautions(),
                        dto.getPDetailImg(),
                        dto.getPAgencyInfo(),
                        dto.getPHost(),
                        dto.getPInquiry());
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
	public int insertCurrentReserve(CurrentReserveDto currentReserveDto) {
        log.info("insertCurrentReserve()");
        
        String sql = "INSERT INTO TBL_CURRENT_RESERVE VALUES(?, ?, ?, ?, ?, ?, ?)";
  
        int result = -1;
        
        try {
            result = jdbcTemplate.update(sql, 
            		currentReserveDto.getC_r_rank(),
            		currentReserveDto.getC_r_period(),
            		currentReserveDto.getP_id(),
            		currentReserveDto.getP_name(),
            		currentReserveDto.getP_theater(),
            		currentReserveDto.getP_thum(),
            		currentReserveDto.getP_category());
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
		
	}
	

	public void insertCurrentReserve(List<List<String>> currentReserve) {
		log.info("insertCurrentReserve()");
	}

	public boolean checkPerfomance(List<String> list) {
		log.info("checkPerfomance()");
		String id = list.get(2);
		
        String sql = "SELECT COUNT(*) FROM TBL_PERFOMANCE WHERE P_ID = ?";
	               
	    int result = -1;
	    
	    try {
	        result = jdbcTemplate.queryForObject(sql, Integer.class ,id);
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    log.info(id);
	    
	    return result > 0 ? true : false;
	}

	public PerfomanceDto selectPerfomance(String id) {
		log.info("selectPerfomance() : " + id);
		
        String sql = "SELECT * FROM TBL_PERFOMANCE WHERE P_ID = ?";
	               
	    int result = -1;
	    
	    try {
	    	RowMapper<PerfomanceDto> rowMapper = 
					BeanPropertyRowMapper.newInstance(PerfomanceDto.class);
			perfomanceDto = jdbcTemplate.queryForObject(sql, rowMapper, id);

			log.info("dto>>>> " + perfomanceDto);
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return perfomanceDto;
	}


}
