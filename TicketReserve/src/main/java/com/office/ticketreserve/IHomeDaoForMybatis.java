package com.office.ticketreserve;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.productpage.CurrentReserveDto;
import com.office.ticketreserve.productpage.PerfomanceDto;

@Mapper
public interface IHomeDaoForMybatis {
	public List<CurrentReserveDto> selectRankOnePerfos();
	public List<PerfomanceDto> selectPerfo(String day);
	public List<PerfomanceDto> selectSearch(Map<String, Object> map);
	public int countSearchResults(String search);
}
