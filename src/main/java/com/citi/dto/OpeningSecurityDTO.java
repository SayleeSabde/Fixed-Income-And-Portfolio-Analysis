package com.citi.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.citi.entity.Security;

public class OpeningSecurityDTO {
	private String isin;
	private String security;
	private String issuerName;
	private double quantity;
	private Date maturityDate;
	private double couponRate;
	
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
			date = new SimpleDateFormat("yyyy-mm-dd").parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.maturityDate = date;
	}
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	
	
}
