package com.citi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "couponinfo")
public class CouponInfo {
	
	@Id
	@Column(name = "id", nullable = false)
	int id;
	
	@Column(name= "isin", nullable = false)
	String isin;
	
	@Column(name = "coupon_dates", nullable = false)
	String couponDates;
	
	@Column(name = "face_value", nullable = false)
	double faceValue;
	
	@Column(name = "day_count_convention", nullable = false)
	String dayCountConvention;
	
	@Column(name = "coupon_rate", nullable = false)
	double couponRate;
	
	@Column(name = "annual", nullable = false)
	boolean annual;
	
	public boolean isAnnual() {
		return annual;
	}

	public void setAnnual(boolean annual) {
		this.annual = annual;
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

	public String getCouponDates() {
		return couponDates;
	}

	public void setCouponDates(String couponDates) {
		this.couponDates = couponDates;
	}

}
