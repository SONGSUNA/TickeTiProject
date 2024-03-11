package com.office.ticketreserve.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	



	public int reviewWrite(String rv_txt, int rv_score, String p_id, String u_id) {
		log.info("reviewWrite");
		
		String p_name = reviewDao.getPname(p_id);
		
		
		
		
		return reviewDao.insertReview(rv_txt,rv_score,p_name,u_id);
	}




	public List<ReviewDto> allReviewsForPid(String p_id) {
		
		String p_name = reviewDao.getPname(p_id);
		
		
		
		
		return reviewDao.allSelectReviewByPname(p_name);
	}





	
	

	
}
