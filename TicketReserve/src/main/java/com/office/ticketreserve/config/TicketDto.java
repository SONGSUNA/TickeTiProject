package com.office.ticketreserve.config;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class TicketDto {
	private int t_no;
	private String p_id;
	private String t_seattype_1;
	private String t_seattype_2;
	private String t_seattype_3;
	private String t_seattype_4;
	private String t_seattype_5;
	private String t_seattype_6;
	private int t_price_1;
	private int t_price_2;
	private int t_price_3;
	private int t_price_4;
	private int t_price_5;
	private int t_price_6;
	private String t_p_date;
	private String t_reg_date;	
	private String t_mod_date;	
}
