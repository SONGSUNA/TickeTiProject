package com.office.ticketreserve.review;

import lombok.Data;

@Data
public class ReviewDto {
	
	private int rv_no;
	private String u_id;
	private String p_name;
	private int rv_score;
	private String rv_txt;
	private int rv_report;
	private String rv_reg_date;
	private String rv_mod_date;
}
