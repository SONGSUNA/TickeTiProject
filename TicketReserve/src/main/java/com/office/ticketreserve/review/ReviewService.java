package com.office.ticketreserve.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.ticketreserve.productpage.PerfomanceDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	



	public int reviewWrite(String rv_txt, int rv_score, String p_id, String u_id) {
		log.info("reviewWrite");
		
		return reviewDao.insertReview(rv_txt,rv_score);
	}



	
}
