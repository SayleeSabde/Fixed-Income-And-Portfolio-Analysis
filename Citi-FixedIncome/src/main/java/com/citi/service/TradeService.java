/**
 * 
 */
package com.citi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
		return getTradeDTOListFromTrade();
	}
	
	private List<GetTradeDTO> getTradeDTOListFromTrade() {
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
			String isin = savedTrade.getMasterSecurityId();
			Optional<MasterSecurity> securityConsidered = masterSecurityRepository.findById(isin);
			getTradeDTO.setSecurity(Security.valueOf(securityConsidered.get().getSecurity()));
			getTradeDTO.setIsin(isin);
			getTradeDTO.setIssuerName(securityConsidered.get().getIssuerName());
			finalTradeList.add(getTradeDTO);
		}
		return finalTradeList;
		
	}

	private void generateMarketPrices() {
		Random random = new Random();
		marketPriceRepository.deleteAll();
		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
		for(MasterSecurity masterSecurity : masterSecurityList) {
			MarketPrice marketPrice = new MarketPrice();
			marketPrice.setIsin(masterSecurity.getIsin());
			System.out.println(masterSecurity.getIssuerName());
			marketPrice.setMarketPrice(masterSecurity.getFaceValue() + random.nextInt(5) - random.nextInt(5) + random.nextDouble());
			marketPriceRepository.save(marketPrice);
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
		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
		ArrayList<MasterSecurity> masterSecList = new ArrayList();
		for (MasterSecurity security : masterSecurityList) {
			masterSecList.add(security);
			
		}
		while(numberOfTrades > 0) {
			int randomIndex = 0 + random.nextInt(12);
			MasterSecurity securityConsidered = masterSecList.get(randomIndex);
			Trade trade = new Trade();
			trade.setQuantity(10 + random.nextInt(500));
			
			//Figure about the date
			trade.setTradeDate(new Date());
			
			double faceValue = securityConsidered.getFaceValue();
			double factor = (0.01 * faceValue * random.nextInt(4)) - (0.01 * faceValue * random.nextInt(3)) + random.nextDouble();
			trade.setPrice(faceValue + factor);
			trade.setBuy(random.nextBoolean());
			trade.setMasterSecurityId(securityConsidered.getIsin());
			tradeRepository.save(trade);
			numberOfTrades--;
		}
	}
	
	

}
