package com.citi.dto;

import org.springframework.stereotype.Component;

@Component
public class OpeningFundsDTO {
	public double openingFunds;

	public double getOpeningFunds() {
		return openingFunds;
	}

	public void setOpeningFunds(double openingFunds) {
		this.openingFunds = openingFunds;
	}
	
	
}
