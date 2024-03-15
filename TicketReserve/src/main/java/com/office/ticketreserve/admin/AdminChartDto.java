package com.office.ticketreserve.admin;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository
@Data
public class AdminChartDto {
	private int r_price;
	private String p_category;
	private String r_reg_date;
	private int daySales;
	private int concertSales;
	private int musicalSales;
	private int theaterSales;
	private int classicSales;
	private int koreanMusicSales;
}
