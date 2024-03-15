package com.office.ticketreserve.review;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IReviewDao {

	
	public int insertReview(@Param("rv_txt") String rv_txt, 
							@Param("rv_score") int rv_score, 
							@Param("p_name") String p_name, 
							@Param("u_id") String u_id);

	public String getPname(String p_id);

	public List<ReviewDto> allSelectReviewByPname(String p_name);

	public ReviewDto getReviewByRv_no(int rv_no);

	public int updateReviewByRv_no(@Param("rv_txt") String rv_txt, 
									@Param("rv_score") int rv_score, 
									@Param("rv_no") int rv_no);

	public int reviewDeleteConfirm(int rv_no);

	public Double getAllStarValue(String p_name);

	
}
