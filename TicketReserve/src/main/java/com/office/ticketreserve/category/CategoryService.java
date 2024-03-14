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
	

	//카테고리 페이지 이동
	public List<PerfomanceDto> goCategory(String category) {
		log.info("goCategory");
		
		return categoryDao.selectConcert(category);
	}
	//랭킹가져오기
	public List<CurrentReserveDto> getRanking(String categoryName) {
		log.info("getRanking");
		return categoryDao.selectRanking(categoryName);
	}
	//개봉예정작
	public List<PerfomanceDto> getUpcoming(String categoryName) {
		log.info("getUpcoming");
		
		return categoryDao.selectUpcoming(categoryName);
	}
	public List<PerfomanceDto> getNewPerfomance(String categoryName) {
		log.info("getNewPerfomance");
		return categoryDao.selectNewPerfomance(categoryName);
	}

}
