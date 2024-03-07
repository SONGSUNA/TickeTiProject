package com.office.ticketreserve.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMailForFindDto {

	    private String emailAddr;
	    private String emailTitle;
	    private String emailContent;
}
