package com.citi.dto;

public class ProfitDTO {	
	
	private String issuername;
	private String isin;
	//realised coupon
	private double couponIncome;
	//unrealised coupon
	private double unrealisedcouponIncome;
	public double getUnrealisedcouponIncome() {
		return unrealisedcouponIncome;
	}

	public void setUnrealisedcouponIncome(double unrealisedcouponIncome) {
		this.unrealisedcouponIncome = unrealisedcouponIncome;
	}
	private double capitalGainLoss;
	private double unrealisedProfitLoss;
	
	public double getUnrealisedProfitLoss() {
		return unrealisedProfitLoss;
	}




	public void setUnrealisedProfitLoss(double unrealisedProfitLoss) {
		this.unrealisedProfitLoss = unrealisedProfitLoss;
	}




	public ProfitDTO() {
		
	}
	



	public String getIssuername() {
		return issuername;
	}




	public void setIssuername(String issuername) {
		this.issuername = issuername;
	}




	public ProfitDTO(String issuername, String isin, double couponIncome, double capitalGainLoss) {
		super();
		this.issuername = issuername;
		this.isin = isin;
		this.couponIncome = couponIncome;
		this.capitalGainLoss = capitalGainLoss;
	}




	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
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
