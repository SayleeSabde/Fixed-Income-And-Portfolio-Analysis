/**
 * 
 */
package com.citi.entity;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;
//import com.citi.entity.MasterSecurity;


@Entity
public class Trade {
	
	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int tradeId;
	
	//@Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Date tradeDate;
	
	//@Column(nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private double price;
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable = false)
	private int quantity;
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(nullable = false)
	private boolean buy;
	
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	//many-to-one is used for establishing foreign key relationships
//	@Column(nullable = false)
//    //@ManyToOne				
//    private MasterSecurity masterSecurity;
	
	
	public int getTradeId() {
		return tradeId;
	}
	public void setTradeId(int tradeId) {
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
//	public MasterSecurity getMasterSecurity() {
//		return masterSecurity;
//	}
//	public void setMasterSecurity(MasterSecurity masterSecurity) {
//		this.masterSecurity = masterSecurity;
//	}

}
