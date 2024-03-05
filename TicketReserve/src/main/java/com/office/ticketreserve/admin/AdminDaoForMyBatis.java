package com.office.ticketreserve.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.office.ticketreserve.user.UserDto;

@Mapper
public interface AdminDaoForMyBatis {

	public AdminDto selectAdminById(String a_id);

	public int insertAdmin(AdminDto adminDto);

	public List<UserDto> selectAllUsers();

}
