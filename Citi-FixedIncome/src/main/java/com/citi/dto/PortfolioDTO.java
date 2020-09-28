package com.citi.dto;

public class PortfolioDTO {
	
	
	
	private String security;
	private String isin;
	private String issuer;
	private String maturiryDate;
	private double couponRate;
	
	public PortfolioDTO() {
		
	}
	
	
	public PortfolioDTO(String security, String isin, String issuer, String maturiryDate, double couponRate) {
		//super();
		this.security = security;
		this.isin = isin;
		this.issuer = issuer;
		this.maturiryDate = maturiryDate;
		this.couponRate = couponRate;
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
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getMaturiryDate() {
		return maturiryDate;
	}
	public void setMaturiryDate(String maturiryDate) {
		this.maturiryDate = maturiryDate;
	}
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	
	

}
