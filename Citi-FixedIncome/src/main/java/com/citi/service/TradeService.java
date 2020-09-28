/**
 * 
 */
package com.citi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.controller.TradeController;
import com.citi.dto.GetTradeDTO;
import com.citi.entity.CouponInfo;
import com.citi.entity.MarketPrice;
import com.citi.entity.MasterSecurity;
import com.citi.entity.Security;
import com.citi.entity.Trade;
import com.citi.repository.CouponInfoRepository;
import com.citi.repository.MarketPriceRepository;
import com.citi.repository.MasterSecurityRepository;
import com.citi.repository.TradeRepository;

/**
 * @author Dhruv
 *
 */
@Service
public class TradeService {
	
	static Logger logger = LoggerFactory.getLogger(TradeController.class.getName());
	
	@Autowired
	TradeRepository tradeRepository;
	
	@Autowired
	MarketPriceRepository marketPriceRepository;
	
	@Autowired 
	MasterSecurityRepository masterSecurityRepository;
	
	@Autowired 
	CouponInfoRepository couponInfoRepository;
	
	
	public TradeRepository getTradeRepository() {
		return tradeRepository;
	}

	public void setTradeRepository(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	public MarketPriceRepository getMarketPriceRepository() {
		return marketPriceRepository;
	}

	public void setMarketPriceRepository(MarketPriceRepository marketPriceRepository) {
		this.marketPriceRepository = marketPriceRepository;
	}

	@Transactional
	public Iterable<GetTradeDTO> generateNewTrades() {
		
		tradeRepository.deleteAll();
		insertRandomTrades();
		generateMarketPrices();
		
		logger.info("++++++++++++++++++++++++++ In Trade Service +++++++++++++++++++++++++ ");
		Iterable<Trade> tradesList = tradeRepository.findAll();
		
		List<GetTradeDTO> finalTradeList = new ArrayList<>();

		for(Trade savedTrade : tradesList ) {
			GetTradeDTO getTradeDTO = new GetTradeDTO();
			getTradeDTO.setTradeId(savedTrade.getTradeId());
			getTradeDTO.setTradeDate(savedTrade.getTradeDate());
			getTradeDTO.setPrice(savedTrade.getPrice());
			getTradeDTO.setQuantity(savedTrade.getQuantity());
			getTradeDTO.setBuy(savedTrade.isBuy());
			//To depend on MasterSecurity
			getTradeDTO.setSecurity(Security.Treasury_Bills);
			getTradeDTO.setIsin("ISIN12345678");
			getTradeDTO.setIssuerName("issuerName");
			finalTradeList.add(getTradeDTO);
		}
		return finalTradeList;
	}
	
	private void generateMarketPrices() {
		Random random = new Random();
		int numberOfRates = 10;
		//int numberOfRates = masterSecurityRepository.count();
		while(numberOfRates > 0) {
			MarketPrice marketPrice = new MarketPrice();
			marketPrice.setIsin(Integer.toString(numberOfRates));
			//Make it foreign key mapping
			marketPrice.setMarketPrice(90 + random.nextDouble());
			//Consider FaceValue too
			marketPriceRepository.save(marketPrice);
			numberOfRates--;
		}
		
//		System.out.println("JUST TEST ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		Iterable<MasterSecurity> slist = masterSecurityRepository.findAll();
//		for (MasterSecurity s : slist) {
//			System.out.println(s.getIsin());
//		}
//		
//		System.out.println("JUST COUPON ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		 Iterable<CouponInfo> clist = couponInfoRepository.findAll();
//		for (CouponInfo c : clist) {
//			System.out.println(c.getIsin());
//		}
		
	}

	@Transactional
	private void insertRandomTrades() {
		
		Random random = new Random();
		int numberOfTrades = 50 + random.nextInt(25);
		while(numberOfTrades > 0) {
			Trade trade = new Trade();
			trade.setQuantity(10 + random.nextInt(30));
			trade.setTradeDate(new Date());
			trade.setPrice(80 + random.nextDouble());
			trade.setBuy(random.nextBoolean());
			tradeRepository.save(trade);
			numberOfTrades--;
		}
	}
	
	

}
