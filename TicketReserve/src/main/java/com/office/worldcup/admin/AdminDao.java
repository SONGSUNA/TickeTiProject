package com.office.worldcup.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao {

	public AdminDto selectAdminById(String adminId);

}
