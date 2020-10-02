package com.citi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "openingsecurity")
public class OpeningSecurity {
	
	@Id
	@Column(name = "Isin", nullable = false)
	private String isin;
	
	@Column(name = "security", nullable = false)
	private String security;
	
	@Column(name = "issuer_name", nullable = false)
	private String issuerName;
	
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	@Column(name = "maturity_date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String maturityDate;
	
	@Column(name = "coupon_rate", nullable = false)
	private double couponRate;
	
	@Column(name = "buy_date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String buyDate;
	
	@Column(name = "buy_price", nullable = false)
	private double buyPrice;
	
	@Column(name = "opening_funds_balance", nullable = false)
	private double openingFunds;
	
	public double getOpeningFunds() {
		return openingFunds;
	}
	public void setOpeningFunds(double openingFunds) {
		this.openingFunds = openingFunds;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
//	public double getPrice() {
//		return price;
//	}
//	public void setPrice(double price) {
//		this.price = price;
//	} 
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String date) {
		this.maturityDate = date;
	}
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	
	
	
	
	
	
}
