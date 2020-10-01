package com.citi.dto;

public class BalanceDTO {
	
	private double realisedCoupon;
	private double unrealisedcoupon;
	public double getUnrealisedcoupon() {
		return unrealisedcoupon;
	}

	public void setUnrealisedcoupon(double unrealisedcoupon) {
		this.unrealisedcoupon = unrealisedcoupon;
	}
	private double realisedYield;
	private double unrealisedYield;
	
	public double getUnrealisedYield() {
		return unrealisedYield;
	}

	public void setUnrealisedYield(double unrealisedYield) {
		this.unrealisedYield = unrealisedYield;
	}
	private double closingFundBalance;
	public BalanceDTO() {
		
	}

	public BalanceDTO(double realisedCoupon, double realisedYield, double closingFundBalance) {
		super();
		this.realisedCoupon = realisedCoupon;
		this.realisedYield = realisedYield;
		this.closingFundBalance = closingFundBalance;
	}

	public double getRealisedCoupon() {
		return realisedCoupon;
	}

	public void setRealisedCoupon(double realisedCoupon) {
		this.realisedCoupon = realisedCoupon;
	}

	public double getRealisedYield() {
		return realisedYield;
	}
	public void setRealisedYield(double realisedYield) {
		this.realisedYield = realisedYield;
	}
	public double getClosingFundBalance() {
		return closingFundBalance;
	}
	public void setClosingFundBalance(double closingFundBalance) {
		this.closingFundBalance = closingFundBalance;
	}

}
