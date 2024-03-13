package com.office.ticketreserve;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class HomeService {
	@Autowired
	IHomeDaoForMybatis homeDao;
	
	public List<CurrentReserveDto> getRankOnePerfo() {
		log.info("[HomeService] getRankOnePerfo()");
		
		List<CurrentReserveDto> rankOnePerfos = homeDao.selectRankOnePerfos();
		
	    List<CurrentReserveDto> randRankInfo = new ArrayList<>();	    
	    randRankInfo = ShuffleNumber(rankOnePerfos);
	    
		return randRankInfo;
	}

	public List<PerfomanceDto> getTodayPerfo() {
		log.info("[HomeService] getTodayPerfo()");
		
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replaceAll("-", ".");
		
		
		List<PerfomanceDto> todayPerfos = homeDao.selectPerfo(today);    
	    	    
		if(todayPerfos.size() >= 5) {
			todayPerfos = ShuffleNumber(todayPerfos);
		   
		} else if (todayPerfos.size() < 5) {
			log.info("todayPerfos.size = " + todayPerfos.size());
			String yesterday = now.plusDays(-1).toString();
			yesterday = yesterday.replaceAll("-", ".");
			List<PerfomanceDto> yesterdayPerfos = homeDao.selectPerfo(yesterday);
			yesterdayPerfos = ShuffleNumber(yesterdayPerfos);
			
			for(int i = 0; i < 6 - todayPerfos.size(); i++) {
				todayPerfos.add(yesterdayPerfos.get(i));
			}
		} 
		return todayPerfos;
	}
	
	public List<PerfomanceDto> getnextPerfo() {
		log.info("[HomeService] getnextPerfo()");
		
		LocalDate now = LocalDate.now();
		String yesterday = now.plusDays(+1).toString();
		yesterday = yesterday.replaceAll("-", ".");
		
		List<PerfomanceDto> yesterdayPerfos = homeDao.selectPerfo(yesterday);    
	    	    
		if(yesterdayPerfos.size() >= 5) {
			yesterdayPerfos = ShuffleNumber(yesterdayPerfos);
		   
		} else if (yesterdayPerfos.size() < 5) {
			log.info("todayPerfos.size = " + yesterdayPerfos.size());
			String next = now.plusDays(+2).toString();
			next = next.replaceAll("-", ".");
			List<PerfomanceDto> nextPerfos = homeDao.selectPerfo(next);
			nextPerfos = ShuffleNumber(nextPerfos);
			
			for(int i = 0; i < 6 - yesterdayPerfos.size(); i++) {
				yesterdayPerfos.add(nextPerfos.get(i));
			}
		} 
		return yesterdayPerfos;
	}

	private <T> List<T> ShuffleNumber(List<T> list) {
		log.info("[HomeService] ShuffleNumber()");
		List<T> shuffleList = new ArrayList<>();
	    List<Integer> randIndex = new ArrayList<>();
		    
		while(true) {
			
	    	long seed = System.currentTimeMillis();
	    	Random random = new Random(seed);
	    	int rand = random.nextInt(list.size());
	    	boolean isOverLap = false;
	    	
	    	for(int j = 0; j<randIndex.size(); j++) {
	    	   	if(randIndex.get(j) == rand)
	    	   		isOverLap = true;
	    	}
	    	
	    	if(isOverLap)
	    		continue;
	    	
	    	shuffleList.add(list.get(rand));
	    	randIndex.add(rand);
	    	
	    	if(randIndex.size() == 5) {
	    		break;
	    	}
	    }
		
		return shuffleList;
		
	}

	public Page<PerfomanceDto> getSearchResult(String search, int pageNo, int pageSize) {
		log.info("[HomeService] getSearchResult()");
		
		int startRow = (pageNo - 1) * pageSize + 1;
	    int endRow = pageNo * pageSize;

	    Map<String, Object> params = new HashMap<>();
	    params.put("p_name", search);
	    params.put("startRow", startRow);
	    params.put("endRow", endRow);

	    List<PerfomanceDto> results = homeDao.selectSearch(params);
	    int totalCount = homeDao.countSearchResults(search);
	    
	    return new PageImpl<>(results, PageRequest.of(pageNo - 1, pageSize), totalCount);
	}

	
}


