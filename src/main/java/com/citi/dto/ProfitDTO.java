package com.citi.dto;

public class ProfitDTO {	
	
	private String security;
	private double couponIncome;
	private double capitalGainLoss;
	
	public ProfitDTO() {
		
	}
	public ProfitDTO(String security, double couponIncome, double capitalGainLoss) {
		super();
		this.security = security;
		this.couponIncome = couponIncome;
		this.capitalGainLoss = capitalGainLoss;
	}
	
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public double getCouponIncome() {
		return couponIncome;
	}
	public void setCouponIncome(double couponIncome) {
		this.couponIncome = couponIncome;
	}
	public double getCapitalGainLoss() {
		return capitalGainLoss;
	}
	public void setCapitalGainLoss(double capitalGainLoss) {
		this.capitalGainLoss = capitalGainLoss;
	}
	
}
