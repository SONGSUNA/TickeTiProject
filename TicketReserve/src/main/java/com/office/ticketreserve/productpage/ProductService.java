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

}
