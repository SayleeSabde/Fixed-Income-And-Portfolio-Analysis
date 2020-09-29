package com.citi.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class CouponInfoDTO {
	
	private int id;
	private String isin;
	private Date couponDates;
	private double faceValue;
	private String dayCountConvention;
	private double couponRate;
	private boolean annual;
	
	public boolean isAnnual() {
		return annual;
	}
	public void setAnnual(boolean annual) {
		this.annual = annual;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public Date getCouponDates() {
		return couponDates;
	}
	public void setCouponDates(String couponDates) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-mm-dd").parse(couponDates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.couponDates = date;
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
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}

	
}
