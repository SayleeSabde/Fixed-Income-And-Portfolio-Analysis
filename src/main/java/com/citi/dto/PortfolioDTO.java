package com.citi.dto;

public class PortfolioDTO {
	private String security;
	private int value;
	private String realisedUnrealised;
	
	public PortfolioDTO() {
		
	}
	
	public PortfolioDTO(String security, int value, String realisedUnrealised) {
		super();
		this.security = security;
		this.value = value;
		this.realisedUnrealised = realisedUnrealised;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getRealisedUnrealised() {
		return realisedUnrealised;
	}
	public void setRealisedUnrealised(String realisedUnrealised) {
		this.realisedUnrealised = realisedUnrealised;
	}

	

}
