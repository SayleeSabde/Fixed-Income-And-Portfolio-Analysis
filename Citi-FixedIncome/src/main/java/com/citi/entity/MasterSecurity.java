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
import javax.persistence.Table;

@Entity
@Table(name = "mastersecurity")
public class MasterSecurity {
	
	@Id
	@Column(name = "Isin", nullable = false)
	String isin;
	
	@Column(nullable = false)
	String security;
	
	@Column(nullable = false)
	String issuerName;
	
	@Column(nullable = false)
	double faceValue;
	
	@Column(nullable = false)
	String dayCountConvention;
	
	@Column(nullable = false)
	String maturityDate;
	
	@Column(nullable = false)
	double couponRate;

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

	
	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public double getCouponRate() {
		return couponRate;
	}

	public void setCouponRate(double couponRate) {
		this.couponRate = couponRate;
	}
	
}
