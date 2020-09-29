/**
 * 
 */
package com.citi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Dhruv
 *
 */

@Entity
public class MarketPrice {
	
	@Id
	@Column(nullable = false)
	String isin;
	
	@Column(nullable = false)
	double marketPrice;

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

}
