package com.office.ticketreserve;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.CurrentReserveDto;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class HomeService {
	@Autowired
	HomeDao homeDao;
	
	public List<CurrentReserveDto> getRankOnePerfo() {
		log.info("[HomeService] getRankOnePerfo()");
		
		List<CurrentReserveDto> rankOnePerfos = homeDao.selectRankOnePerfos();
		
		return rankOnePerfos;
	}
}


