package com.office.ticketreserve.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.reservation.ReservationDto;

@Mapper
public interface IUserDaoForMybatis {
	public boolean isUser(String u_id) ;
	public int insertUser(UserDto userDto);
	public UserDto selectUserForLogin(UserDto userDto);

	/*
	 * public List<UserDto> selectAllUsers; public static UserDto
	 * selectAdminById(String u_id_check);
	 */
	public UserDto selectUser(int u_no);
	public int deleteUser(int u_no) ;
//	public String dofindIdFromDB(String u_name, String u_mail);
	//예매 내역 불러오기 
	public List<ReservationDto> getMyTicketinfo(String u_id);
	public List<String>  getPerfomanceId(String tNo);
	public List<PerfomanceDto> getPerfomaceInfo(String p_id);	


}
