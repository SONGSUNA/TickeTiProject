package com.office.ticketreserve.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReviewService {
	
	@Autowired
	IReviewDao reviewDao;
	



	public ReviewDto reviewWrite(String rv_txt, int rv_score, String p_id, String u_id) {
		log.info("reviewWrite");
		
		String p_name = reviewDao.getPname(p_id);

		int result = reviewDao.insertReview(rv_txt,rv_score,p_name,u_id);
		
		if (result > 0) {
			return reviewDao.getReviewsByUser(u_id, p_id);
		}
		return null;
	}




	public List<ReviewDto> allReviewsForPid(String p_id) {
		
		String p_name = reviewDao.getPname(p_id);

		return reviewDao.allSelectReviewByPname(p_name);
	}




	public ReviewDto reviewModify(int rv_no) {
		
		return reviewDao.getReviewByRv_no(rv_no);
	}




	public ReviewDto reviewModifyConfirm(String rv_txt, int rv_score, int rv_no) {
	    
		reviewDao.updateReviewByRv_no(rv_txt, rv_score, rv_no);
	  
	    return reviewDao.getReviewByRv_no(rv_no);
	}




	public int reviewDeleteConfirm(int rv_no) {
		
		return reviewDao.reviewDeleteConfirm(rv_no);
	}




	public Double starAvg(String p_id) {
		
		String p_name = reviewDao.getPname(p_id);
		
		Double result = reviewDao.getAllStarValue(p_name);	
		
		return result != null ? result : 0.0;
	}




	public ReviewDto reviewModifyRefresh(int rv_no) {
		
		return reviewDao.getReviewByRv_no(rv_no);
	}

	public boolean hasUserReviewedPerformance(String u_id, String p_id) {
	    return reviewDao.countReviewsByUser(u_id, p_id) > 0;
	}
	

	
}
