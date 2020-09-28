package com.citi.dto;

public class ProfitDTO {	
	
	private String security;
	private String isin;
	private double profitLoss;
	private String realisedOrUnrealised;
	
	public ProfitDTO() {

	}
	
	public ProfitDTO(String security, String isin, double profitLoss, String realisedOrUnrealised) {
		//super();
		this.security = security;
		this.isin = isin;
		this.profitLoss = profitLoss;
		this.realisedOrUnrealised = realisedOrUnrealised;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public double getPl() {
		return profitLoss;
	}
	public void setPl(double pl) {
		this.profitLoss = pl;
	}
	public String getRealisedOrUnrealised() {
		return realisedOrUnrealised;
	}
	public void setRealisedOrUnrealised(String realisedOrUnrealised) {
		this.realisedOrUnrealised = realisedOrUnrealised;
	}

}
