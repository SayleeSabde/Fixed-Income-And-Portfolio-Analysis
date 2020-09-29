package com.citi.service;

import java.nio.channels.SeekableByteChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.citi.dto.GetTradeDTO;
import com.citi.dto.MasterSecurityDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.entity.MasterSecurity;
import com.citi.entity.Trade;

public class SummaryService {
	
	private TradeService tradeservice;
	private MasterSecurity mastersecurity;
	
	//tradeservice.getTradeList();
	//masterList = mastersecurity.getSecurityList();
	
	public double CalcCouponTot() {
		HashMap<String, Double> hmap = new HashMap<String,Double>();
		double couponTot = 0;
		for(MasterSecurity m: masterList) {
			
			int id = m.getPid();
			String isin = m.getIsin();
			double currentCoupon;
			currentCoupon = CalcCouponSec(id);
			double prevCoupon = hmap.get(isin);
			currentCoupon = currentCoupon+prevCoupon;
			hmap.put(isin, currentCoupon);
			couponTot = couponTot + currentCoupon;
		}
		
		
		return couponTot;
		
	
	}
	public double CalcCouponSec(int pid) {
		
	
		
		
		return 0;
		
		
	}
	
	
	public double finalFunds() {
		//getting the data from database
		TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= t.getTradeDTOListFromTrade() ;
		MasterSecurityService masterSecurityService = new MasterSecurityService();
		List<MasterSecurityDTO> master = masterSecurityService.getMasterSecuritiesDTOList();
		//initial balance
		OpeningFundsService openFundService = new OpeningFundsService();
		double initialBalance  = openFundService.getOpeningFunds().getOpeningFunds();
		
		double ans = 0;
		double buyTrades = 0;
		double sellTrades = 0;
		double tradesPrice = 0;
		double tradesQuantity= 0;
		
		double coupon = 0;
		double matured = 0;
		String maturedDate;
		double maturedGloabal=0;
		
		for(GetTradeDTO data : tradeData ) {
			tradesPrice = data.getPrice();
			tradesQuantity = data.getQuantity();
			if(data.isBuy()) {
				buyTrades = buyTrades + (tradesPrice*tradesQuantity);
			}else {
				sellTrades = sellTrades + (tradesPrice*tradesQuantity);
			}
			
		}
		
		coupon = CalcCouponTot();
		
		for(MasterSecurityDTO m: master) {
			//maturedDate = m.getMaturityDate();
			//Date d1 = df.parse(maturedDate);
			//Date d2 = df.parse("2021-03-31");
			Date d1 = m.getMaturityDate();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d2 = null;
			try {
				d2 = new SimpleDateFormat("yyyy-mm-dd").parse("2021-02-31");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		    //Date 1 occurs before Date 2
		    if(d1.compareTo(d2) <= 0) {
		    	//principle value
		    	String y = m.getIsin();
		    	int q =0;
		    	matured = (m.getFaceValue());
		    	for(GetTradeDTO x : tradeData) {
		    		if(y== x.getIsin()) {
		    			q += x.getQuantity(); 
		    		}
		    	}
		    	matured*=q;
		    }
		    maturedGloabal+=matured;
		         
		}
		ans = initialBalance-buyTrades+sellTrades +coupon+maturedGloabal;
		return ans;
	}
	
	public HashMap<String, Double> finalSec() {
		TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= t.getTradeDTOListFromTrade() ;
		
		HashMap<String, Double> hmap = new HashMap<String,Double>();
		
		OpeningSecurityService s = new OpeningSecurityService();
		List<OpeningSecurityDTO> opening = s.getOpeningSecurityDTOList();
		ListIterator<OpeningSecurityDTO> listIterator = opening.listIterator();
		 
		while(listIterator.hasNext()) {
		    hmap.put(listIterator.next().getIsin(), listIterator.next().getQuantity());
		}
		double buyTrades = 0;
		double sellTrades = 0;
		double tradesQuantity= 0;
		double update =0;
		
		for(GetTradeDTO data : tradeData ) {
			tradesQuantity = data.getQuantity();
			if(data.isBuy()) {
				buyTrades = tradesQuantity;
				update = hmap.get(data.getIsin());
				update+=(buyTrades);
				hmap.put(data.getIsin(), update);
				
			}else {
				sellTrades =  tradesQuantity;
				update = hmap.get(data.getIsin());
				update-=(sellTrades);
				hmap.put(data.getIsin(), update);
			}
			
		}
		return hmap;
	}
	
	public double realisedProfitsTot() {
		
		MasterSecurityService masterSecurityService = new MasterSecurityService();
		List<MasterSecurityDTO> master = masterSecurityService.getMasterSecuritiesDTOList();
		
		double totalProfit = 0;
		
		for(MasterSecurityDTO m: master) {
			totalProfit += realisedProfitSec(m.getIsin());
		}
		
		
		return totalProfit;
	}

	public double realisedProfitSec(String i) {
		TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= t.getTradeDTOListFromTrade() ;
		
		double ans = 0;
		double buyTrades = 0;
		double sellTrades = 0;
		double tradesPrice = 0;
		double tradesQuantity= 0;
		
		double totalBuy = 0;
		double totalSell = 0;
		
		
		for(GetTradeDTO data : tradeData ) {
			if(data.getIsin()==i) {
				tradesPrice = data.getPrice();
				tradesQuantity = data.getQuantity();
				if(data.isBuy()) {
					buyTrades = buyTrades + (tradesPrice*tradesQuantity);
					totalBuy += tradesQuantity;
				}else {
					sellTrades = sellTrades + (tradesPrice*tradesQuantity);
					totalSell += tradesQuantity;
				}
			}	
		}
		ans = ((sellTrades/totalSell)-(buyTrades/totalBuy))*totalSell;
		
		return ans;
	}
	
	public double unrealisedProfitsTot() {
		
		TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= t.getTradeDTOListFromTrade() ;
		
		return 0;
	}
	
	public double unrealisedProfitsSec() {
		TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= t.getTradeDTOListFromTrade() ;
		
		
		return 0;
	}
}
