package com.office.ticketreserve.reservation;

import lombok.Data;

@Data
public class ReservationDto {
	private int r_no;
	private String u_id;
	private String r_sub_phone;
	private int t_no;
	private String r_date;
	private String r_time;
	private int r_take_ticket;
	private int r_price;
	private int r_payment_state;
	private String t_seat;
	private String r_reg_date;
	private String r_mod_date;
}
