package com.office.ticketreserve.productpage;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class PerfomanceDto {
	private String  p_id;
    private String  p_name;
    private String  p_start_date;
    private String  p_end_date;
    private String  p_time;
    private String  p_grade;
    private String  p_theater;
    private String  p_place;
    private float	p_latitude;
    private float	p_lognitude;	
    private String  p_thum;
    private String  p_category;
    private int 	p_max_reserve;
    private int 	p_now_reserve;
    private String  p_running_time;
    private String  p_characters;
    private String  p_ticket;
    private int 	p_like;
    private String  p_detail_cautions;
    private String  p_detail_img;
    private String  p_agency_info;
    private String  p_host;
    private String  p_inquiry;
    private String  p_reg_date;
    private String  p_mod_date;
}