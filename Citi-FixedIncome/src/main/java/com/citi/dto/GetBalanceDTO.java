/**
 * 
 */
package com.citi.dto;

import java.util.Date;

/**
 * @author Dhruv
 *
 */
public class GetBalanceDTO {
	private String isin;
	private Date tradeDate;
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

}
