package com.office.ticketreserve.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.user.UserDto;

@Mapper
public interface AdminDaoForMyBatis {

	public AdminDto selectAdminById(String a_id);

	public int insertAdmin(AdminDto adminDto);

	public List<UserDto> selectAllUsers();

	public List<UserDto> selectUsersById(String u_id);

	public List<UserDto> selectUsersByName(String u_name);

	public List<UserDto> selectUsersByMail(String u_mail);
	
	public AdminDto selectAdminByIdPw(String a_id, String a_pw);

}
