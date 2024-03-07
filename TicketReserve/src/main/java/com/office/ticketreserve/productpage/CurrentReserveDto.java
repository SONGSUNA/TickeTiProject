package com.office.ticketreserve.productpage;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class CurrentReserveDto {	
	private String p_name;
	private int c_r_rank;
	private String c_r_period;
	private String p_id;
	private String p_thum;
	private String p_theater;
	private String p_category;
}
