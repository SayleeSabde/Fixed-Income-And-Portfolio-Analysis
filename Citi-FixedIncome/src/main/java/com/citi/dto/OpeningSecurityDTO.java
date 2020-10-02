package com.citi.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OpeningSecurityDTO {
	private String isin;
	private String security;
	private String issuerName;
	private double quantity;
	private Date maturityDate;
	private double couponRate;
	private Date buyDate;
	private double buyPrice;
	
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(buyDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.buyDate = date;
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
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String string) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		this.maturityDate = date;
	}
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	
	
}
