package com.citi.dto;

public class BalanceDTO {
	
	private double accruedCoupon;
	private double realisedYield;
	private double unrealisedYield;
	//private double closingFundBalance;
	public BalanceDTO() {
		
	}
	public BalanceDTO(double accruedCoupon, double realisedYield, double unrealisedYield) {
		super();
		this.accruedCoupon = accruedCoupon;
		this.realisedYield = realisedYield;
		this.unrealisedYield = unrealisedYield;
	}
	
	public double getAccruedCoupon() {
		return accruedCoupon;
	}
	public void setAccruedCoupon(double accruedCoupon) {
		this.accruedCoupon = accruedCoupon;
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
//	public double getClosingFundBalance() {
//		return closingFundBalance;
//	}
//	public void setClosingFundBalance(double closingFundBalance) {
//		this.closingFundBalance = closingFundBalance;
//	}
	
}
