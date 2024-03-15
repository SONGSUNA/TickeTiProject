package com.office.ticketreserve.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import com.office.ticketreserve.config.TicketDto;
import com.office.ticketreserve.productpage.PerfomanceDto;
import com.office.ticketreserve.reservation.ReservationDto;
import com.office.ticketreserve.user.UserDto;

@Mapper
public interface AdminDaoForMyBatis {

	public AdminDto selectAdminById(String a_id);

	public int insertAdmin(AdminDto adminDto);

	public List<UserDto> selectAllUsers();

	public List<UserDto> selectUsersById(String u_id);

	public List<UserDto> selectUsersByName(String u_name);

	public List<UserDto> selectUsersByMail(String u_mail);

	public List<AdminDto> selectAllAdmins();

	public List<AdminDto> selectAdminsById(String a_id);

	public List<AdminDto> selectAdminsByName(String a_name);

	public List<AdminDto> selectAdminsByMail(String a_mail);

	public void deleteAdmin(int a_no);

	public PerfomanceDto selectPerfomanceById(String id);

	public void insertPerfomance(PerfomanceDto perfomanceDto);

	public void insertPerfomanceNotDetailCautions(PerfomanceDto perfomanceDto);

	public void updateAdminWitoutPw(AdminDto adminDto);

	public void updateUserWithoutPw(UserDto userDto);

	public List<PerfomanceDto> selectAllPerfomance();

	public List<PerfomanceDto> selectAllPerfomanceNoTicket();

	public List<PerfomanceDto> selectAllPerfomanceByName(String p_name);

	public TicketDto selectTicketByPId(String p_id);

	public int updateTicket(TicketDto ticketDto);
	
	public int insertTicket(TicketDto ticketDto);

	public boolean updatePerformanceTicket(@Param("p_ticket") String p_ticket,
										   @Param("p_time") String p_time, 
										   @Param("p_id") String p_id);

	public int updatePfWithoutImg(PerfomanceDto perfomanceDto);

	public int updatePfWithThumb(PerfomanceDto perfomanceDto);

	public int updatePfWithDetailImg(PerfomanceDto perfomanceDto);

	public int updatePfWithImg(PerfomanceDto perfomanceDto);

	public List<ReservationDto> selectRsvInfo(@Param("stDate") String stDate, 
											@Param("edDate") String edDate);

	public TicketDto selectTicket(int t_no);
	
}
