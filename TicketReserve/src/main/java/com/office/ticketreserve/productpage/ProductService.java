package com.office.ticketreserve.productpage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	ProductDao productDao;
	public PerfomanceDto productPage(String pId) {
		
		
		
		return productDao.selectProduct(pId);
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
