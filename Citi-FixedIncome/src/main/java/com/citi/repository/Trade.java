/**
 * 
 */
package com.citi.repository;
import java.util.Date;

/**
 * @author Dhruv
 *
 */
public class Trade {
	
	String tradeId;
	Date tradeDate;
	double price;
	int quantity;
	boolean buy;
	MasterSecurity masterSecurity;
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
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
	public MasterSecurity getMasterSecurity() {
		return masterSecurity;
	}
	public void setMasterSecurity(MasterSecurity masterSecurity) {
		this.masterSecurity = masterSecurity;
	}

}
