package com.office.ticketreserve.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@Mapper
public interface IUserDaoForMybatis {
	public boolean isUser(String u_id) ;
	public int insertUser(UserDto userDto);
	public UserDto selectUserForLogin(UserDto userDto);

	/*
	 * public List<UserDto> selectAllUsers; public static UserDto
	 * selectAdminById(String u_id_check);
	 */
	public int deleteUser(int u_no) ;
	public String dofindIdFromDB(String u_name, String u_mail);


}
