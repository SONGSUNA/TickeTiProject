package com.office.worldcup.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDaoForMyBatis {

	public AdminDto selectAdminById(String adminId);

	public int insertAdmin(AdminDto adminDto);

}
