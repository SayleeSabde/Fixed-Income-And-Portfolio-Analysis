package com.citi.dto;

import java.util.Date;
import java.util.GregorianCalendar;

import com.citi.entity.Security;

public class GetTradeDTO {
	String tradeId;
	Security security;
	String isin;
	String issuerName;
//	Date tradeDate;
	GregorianCalendar tradeDate;
	double price;
	int quantity;
	boolean buy;
	
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String i) {
		this.tradeId = i;
	}
	public Security getSecurity() {
		return security;
	}
	public void setSecurity(Security security) {
		this.security = security;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public GregorianCalendar getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(GregorianCalendar object) {
		this.tradeDate = object;
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
	
	

}
