package com.office.ticketreserve.reservation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationDao {

	List<ReservationDto> selectReservation(Map<String, Object> dateTime);

}
