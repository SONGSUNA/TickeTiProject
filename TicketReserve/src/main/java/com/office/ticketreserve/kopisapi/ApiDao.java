package com.office.ticketreserve.kopisapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ApiDao {
    
	@Autowired
    JdbcTemplate jdbcTemplate;

    public int insertPerfomance(PerfomanceDto dto) {
        log.info("insertPerfomance()");
        
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
}
