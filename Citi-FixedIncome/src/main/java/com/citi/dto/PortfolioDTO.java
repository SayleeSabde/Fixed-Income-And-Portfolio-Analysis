package com.citi.dto;

import java.util.Date;

public class PortfolioDTO {
	private String issuername;
	public String getIssuername() {
		return issuername;
	}

	public void setIssuername(String issuername) {
		this.issuername = issuername;
	}

	private String isin;
	private int balance;
	private Date maturityDate;
	private double finalValuation;
	//private String realisedUnrealised;
	
	public double getFinalValuation() {
		return finalValuation;
	}

	public void setFinalValuation(double finalValuation) {
		this.finalValuation = finalValuation;
	}

	public PortfolioDTO() {
		
	}



	public PortfolioDTO(String issuername, String isin, int balance, Date maturityDate) {
		super();
		this.issuername = issuername;
		this.isin = isin;
		this.balance = balance;
		this.maturityDate = maturityDate;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date date) {
		this.maturityDate = date;
	}

//	public String getRealisedUnrealised() {
//		return realisedUnrealised;
//	}
//
//	public void setRealisedUnrealised(String realisedUnrealised) {
//		this.realisedUnrealised = realisedUnrealised;
//	}

	
}
