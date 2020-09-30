package com.citi.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MasterSecurityDTO {
	
	private String isin;
	
	private String security;
	
	private String issuerName;
	
	private double faceValue;
	
	private String dayCountConvention;
	
	private Date maturityDate;
	
	private double couponRate;
	
	private Date issueDate;


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

	public double getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}

	public String getDayCountConvention() {
		return dayCountConvention;
	}

	public void setDayCountConvention(String dayCountConvention) {
		this.dayCountConvention = dayCountConvention;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(maturityDate);
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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(issueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.issueDate = date;
	}


}