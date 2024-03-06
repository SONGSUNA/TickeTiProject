package com.office.ticketreserve.productpage;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class perfomanceDto {
	private String pId;
    private String pName;
    private String pStartDate;
    private String pEndDate;
    private String pGrade;
    private String pTheater;
    private String pPlace;
    private String pThum;
    private String pCategory;
    private int pMaxReserve;
    private int pNowReserve;
    private String pRunningTime;
    private String pCharacters;
    private String pTicket;
    private int pLike;
    private String pDetailCautions;
    private String pDetailImg;
    private String pAgencyInfo;
    private String pHost;
    private String pInquiry;
    private String pRegDate;
    private String pModDate;
}
