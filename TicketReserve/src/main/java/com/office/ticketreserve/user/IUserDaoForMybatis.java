package com.office.ticketreserve.user;

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
	public ReservationDto getMyTicketinfo(String u_id);
	public String getPerfomanceId(int t_no);
	public PerfomanceDto getPerfomanceName(String p_id);


}
