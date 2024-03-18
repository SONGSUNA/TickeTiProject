package com.office.ticketreserve.reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		
		haspMap.put("perfomanceDto", perfomanceDto);
		haspMap.put("p_start_date", perfomanceDto.getP_start_date());
		haspMap.put("p_end_date", perfomanceDto.getP_end_date());
		haspMap.put("p_time", perfomanceDto.getP_time());
		haspMap.put("p_max_reserve", perfomanceDto.getP_max_reserve());
		haspMap.put("ticketDto", ticketDto);
		
		return haspMap;
	}

	
	public List<Integer> getReserveSeat(String t_no, String date, String time) {
		log.info("[ReservationService] getReserveSeat()");
		
		Map<String, Object> map = new HashMap<>();
		map.put("t_no", t_no);
		map.put("date", date);
		map.put("time", convertTimeFormat(time));
		
		List<ReservationDto> reservationDtos = reservationDao.selectReservation(map);
		
		List<Integer> reserveSeats = new ArrayList<>();
		
		for (ReservationDto reservationDto : reservationDtos) {
	        String seatString = reservationDto.getT_seat();
	        int seatNumber = Integer.parseInt(seatString);
	        reserveSeats.add(seatNumber);
	    }
		
		return reserveSeats.isEmpty() ? null : reserveSeats;
	}
	
	// util ========================================================================================
	public static String convertTimeFormat(String time) {
		String numberOnly = time.replaceAll("[^0-9]", "");
		
		int length = numberOnly.length();
		
		String formattedTime;
		if (length >= 4) {
			formattedTime = numberOnly.substring(length - 4, length - 2) + ":" + numberOnly.substring(length - 2) + ":00";
		} else if (length >= 2) {
			formattedTime = "00:" + numberOnly.substring(length - 2) + ":00";
		} else {
			formattedTime = "00:00:00";
		}
		
		return formattedTime;
	}
}
	

