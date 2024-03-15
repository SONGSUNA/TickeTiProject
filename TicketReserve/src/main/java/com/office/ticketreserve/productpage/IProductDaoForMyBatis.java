package com.office.ticketreserve.productpage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IProductDaoForMyBatis {

	public PerfomanceDto selectProduct(String p_id);

	public int insertLike(@Param("p_id") String p_id,@Param("u_id") String u_id);
	
	public void updateLike(String p_id);
	
	public boolean selectIsLiked(@Param("p_id") String p_id,@Param("u_id") String u_id);
	
	public int deleteLike(@Param("p_id") String p_id,@Param("u_id") String u_id);
	
	public void updateUnlike(String p_id);
}
