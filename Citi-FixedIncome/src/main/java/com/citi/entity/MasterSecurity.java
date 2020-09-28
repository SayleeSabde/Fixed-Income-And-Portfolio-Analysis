/**
 * 
 */
package com.citi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.validation.constraints.Size;

@Entity
public class MasterSecurity {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int masterSecurityId;
	//Annotation for enum
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)Security security;
	private String issuerName;
	@Column(length = 12,nullable=false)
	//@Size(min = 12, max = 12)
	private String isin;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private int quantity;
	@Column(nullable = false)
	private boolean buy;
	@Column(nullable = false)
	private double faceValue;
	@Column(nullable = false)
	private double couponRate;
    @ElementCollection  
    @Column(nullable = false)
	private List<Date> couponDates = new ArrayList<Date>();
    @Enumerated(EnumType.STRING)DayCountConvention dayCountConvention;
    @Column(nullable = false)
	Date maturityDate;
	
	public int getMasterSecurityId() {
		return masterSecurityId;
	}
	public void setMasterSecurityId(int masterSecurityId) {
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
