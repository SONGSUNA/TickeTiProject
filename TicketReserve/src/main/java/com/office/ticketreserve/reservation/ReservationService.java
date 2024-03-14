package com.office.ticketreserve.reservation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.admin.AdminDaoForMyBatis;
import com.office.ticketreserve.config.TicketDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReservationService {
	
	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	AdminDaoForMyBatis adminDao;

	public Map<String, Object> getInfoForReservation(String p_id) {
		log.info("[ReservationService] getInfoForReservation()");
		
		TicketDto ticketDto = adminDao.selectTicketByPId(p_id);
		PerfomanceDto perfomanceDto = adminDao.selectPerfomanceById(p_id);
		
		HashMap<String, Object> haspMap = new HashMap<>();
		
		haspMap.put("p_start_date", perfomanceDto.getP_start_date());
		haspMap.put("p_end_date", perfomanceDto.getP_end_date());
		haspMap.put("p_time", perfomanceDto.getP_time());
		haspMap.put("p_max_reserve", perfomanceDto.getP_max_reserve());
		haspMap.put("ticketDto", ticketDto);
		
		return haspMap;
	}

}
