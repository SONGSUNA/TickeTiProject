package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class ProductService {
	@Autowired
	IProductDaoForMyBatis productDao;
	
	
	public PerfomanceDto productPage(String p_id) {
				
		return productDao.selectProduct(p_id);
	}
	
	
	public int productLike(String p_id, String u_id) {
		
		return productDao.insertLike(p_id,u_id);
	}
	
	
	public boolean isLiked(String p_id, String u_id) {
	    
		return productDao.selectIsLiked(p_id, u_id);
	}
	
	
	
	public int toggleLike(String p_id, String u_id) {
	    
		boolean isLiked = productDao.selectIsLiked(p_id, u_id);
		
	    if (isLiked) {
	        return productDao.deleteLike(p_id, u_id);
	    } else {
	        return productDao.insertLike(p_id, u_id);
	    }
	}
}
