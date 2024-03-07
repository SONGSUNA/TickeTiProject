package com.office.ticketreserve.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.productpage.PerfomanceDto;
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

}
