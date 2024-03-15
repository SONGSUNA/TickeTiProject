package com.office.ticketreserve.category;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

@Mapper
public interface ICategoryDaoForMyBatis {


	public List<PerfomanceDto> selectConcert(String category);

	public List<CurrentReserveDto> selectRanking(String categoryNameForRanking);
	
	public List<PerfomanceDto> selectUpcoming(String categoryName);

	public List<PerfomanceDto> selectNewPerfomance(String categoryName);
	 

}
