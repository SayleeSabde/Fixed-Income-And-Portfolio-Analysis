/**
 * 
 */
package com.citi.repository;

import java.util.Date;
import java.util.List;

/**
 * @author Dhruv
 *
 */
public class MasterSecurity {
	
	String masterSecurityId;
	Security security;
	String issuerName;
	String isin;
	double price;
	int quantity;
	boolean buy;
	double faceValue;
	double couponRate;
	List<Date> couponDates;
	DayCountConvention dayCountConvention;
	Date maturityDate;
	
	public String getMasterSecurityId() {
		return masterSecurityId;
	}
	public void setMasterSecurityId(String masterSecurityId) {
		this.masterSecurityId = masterSecurityId;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isBuy() {
		return buy;
	}
	public void setBuy(boolean buy) {
		this.buy = buy;
	}
	public double getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(double faceValue) {
		this.faceValue = faceValue;
	}
	public double getCouponRate() {
		return couponRate;
	}
	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	public List<Date> getCouponDates() {
		return couponDates;
	}
	public void setCouponDates(List<Date> couponDates) {
		this.couponDates = couponDates;
	}
	public DayCountConvention getDayCountConvention() {
		return dayCountConvention;
	}
	public void setDayCountConvention(DayCountConvention dayCountConvention) {
		this.dayCountConvention = dayCountConvention;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	
}
