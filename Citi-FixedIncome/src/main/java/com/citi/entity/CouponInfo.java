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
