package com.office.ticketreserve.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDaoForMyBatis {

	public AdminDto selectAdminById(String a_id);

	public int insertAdmin(AdminDto adminDto);

}
