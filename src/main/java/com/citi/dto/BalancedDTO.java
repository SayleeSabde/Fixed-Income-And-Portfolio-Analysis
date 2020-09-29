package com.citi.dto;

public class BalancedDTO {
	
	private double couponAccrued;
	private double realisedYield;
	private double unrealisedYield;
	private double closingFundBalance;
	
	public BalancedDTO() {
		
	}
	
	public BalancedDTO(double couponAccrued, double realisedYield, double unrealisedYield,
			double closingFundBalance) {
		super();
		this.couponAccrued = couponAccrued;
		this.realisedYield = realisedYield;
		this.unrealisedYield = unrealisedYield;
		this.closingFundBalance = closingFundBalance;
	}
	
	
	public double getCouponAccrued() {
		return couponAccrued;
	}
	public void setCouponAccrued(double couponAccrued) {
		this.couponAccrued = couponAccrued;
	}
	public double getRealisedYield() {
		return realisedYield;
	}
	public void setRealisedYield(double realisedYield) {
		this.realisedYield = realisedYield;
	}
	public double getUnrealisedYield() {
		return unrealisedYield;
	}
	public void setUnrealisedYield(double unrealisedYield) {
		this.unrealisedYield = unrealisedYield;
	}
	public double getClosingFundBalance() {
		return closingFundBalance;
	}
	public void setClosingFundBalance(double closingFundBalance) {
		this.closingFundBalance = closingFundBalance;
	}
	
	

}
