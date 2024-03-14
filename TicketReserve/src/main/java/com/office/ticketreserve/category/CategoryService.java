package com.office.ticketreserve.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;
@Service
@Log4j2
public class CategoryService {
	
	@Autowired
	CategoryDao categoryDao;
	
	public List<PerfomanceDto> goConcert() {
		
		
		
		return categoryDao.selectConcert();
	}

	public List<CurrentReserveDto> getRanking(String categoryNameForRanking) {
		
		return categoryDao.selectRanking(categoryNameForRanking);
	}

}
