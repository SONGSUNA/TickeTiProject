package com.office.ticketreserve.kopisapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.config.TicketDto;
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
        log.info("insertPerfomance() : " + dto.getP_id());
        String sql = "INSERT INTO TBL_PERFOMANCE ("
                    + "P_ID, "
                    + "P_NAME, "
                    + "P_START_DATE, "
                    + "P_END_DATE, "
                    + "P_GRADE, "
                    + "P_THEATER, "
                    + "P_PLACE, "
                    + "P_LATITUDE, "
                    + "P_LOGNITUDE, "                    
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
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP"
                    + ")";
        int result = -1;
        
        try {
            result = jdbcTemplate.update(sql, 
            			dto.getP_id(), 
            			dto.getP_name(), 
            			dto.getP_start_date(), 
            			dto.getP_end_date(),
            			dto.getP_grade(),
                        dto.getP_theater(),
                        dto.getP_place(),
                        dto.getP_latitude(),
                        dto.getP_lognitude(),                        
                        dto.getP_thum(),
                        dto.getP_category(),
                        dto.getP_max_reserve(),
                        dto.getP_now_reserve(),
                        dto.getP_running_time(),
                        dto.getP_characters(),
                        dto.getP_ticket(),
                        dto.getP_like(),
                        dto.getP_detail_cautions(),
                        dto.getP_detail_img(),
                        dto.getP_agency_info(),
                        dto.getP_host(),
                        dto.getP_inquiry());
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

	public List<PerfomanceDto> selectPerfomanceAll() {
		log.info("selectPerfomanceAll()");
		String sql = "SELECT * FROM TBL_PERFOMANCE";
        
	    int result = -1;
	    
	    List<PerfomanceDto> perfomanceDtos = new ArrayList<>();
	    
	    try {
	    	 perfomanceDtos = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(PerfomanceDto.class));
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return perfomanceDtos;
	}

	public int insertTicket(TicketDto ticketDto) {
		 log.info("insertTicket()");
	        
		 String sql = "INSERT INTO TBL_TICKET ("
			 		+ "T_NO, "
			 		+ "	P_ID ,"
			 		+ "	T_SEATTYPE_1, "
			 		+ "	T_SEATTYPE_2, "
			 		+ "	T_SEATTYPE_3, "
			 		+ "	T_SEATTYPE_4, "
			 		+ "	T_SEATTYPE_5, "
			 		+ "	T_SEATTYPE_6, "
			 		+ "	T_PRICE_1, "
			 		+ "	T_PRICE_2, "
			 		+ "	T_PRICE_3, "
			 		+ "	T_PRICE_4, "
			 		+ "	T_PRICE_5, "
			 		+ "	T_PRICE_6, "
			 		+ "	T_P_DATE, "
			 		+ "	T_REG_DATE, "
			 		+ "	T_MOD_DATE)"
			 		+ "VALUES ("
			 		+ "TICKET_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP"
			 		+ ")";
	  
	        int result = -1;
	        
	        try {
	            result = jdbcTemplate.update(sql, 
	            		ticketDto.getP_id(),
	            		ticketDto.getT_seattype_1(),
	            		ticketDto.getT_seattype_2(),
	            		ticketDto.getT_seattype_3(),
	            		ticketDto.getT_seattype_4(),
	            		ticketDto.getT_seattype_5(),
	            		ticketDto.getT_seattype_6(),
	            		ticketDto.getT_price_1(),
	            		ticketDto.getT_price_2(),
	            		ticketDto.getT_price_3(),
	            		ticketDto.getT_price_4(),
	            		ticketDto.getT_price_5(),
	            		ticketDto.getT_price_6(),
	            		ticketDto.getT_p_date()
	            		);
	            
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return result;
	}
}
