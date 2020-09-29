/**
 * 
 */
package com.citi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.controller.TradeController;
import com.citi.dto.GetTradeDTO;
import com.citi.dto.MasterSecurityDTO;
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
@SuppressWarnings("deprecation")
public class TradeService {
	
	static Logger logger = LoggerFactory.getLogger(TradeController.class.getName());
	
	@Autowired
	TradeRepository tradeRepository;
	
	@Autowired
	MarketPriceRepository marketPriceRepository;
	
	@Autowired 
	MasterSecurityRepository masterSecurityRepository;
	
	@Autowired 
	MasterSecurityService masterSecurityService;
	
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
	
	@Transactional
	public List<GetTradeDTO> getTradeDTOListFromTrade() {
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

	public void generateMarketPrices() {
		Random random = new Random();
		marketPriceRepository.deleteAll();
		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
		for(MasterSecurity masterSecurity : masterSecurityList) {
			MarketPrice marketPrice = new MarketPrice();
			marketPrice.setIsin(masterSecurity.getIsin());
			System.out.println(masterSecurity.getIssuerName());
			double faceValue = masterSecurity.getFaceValue();
			double factor = (0.0001 * faceValue * random.nextInt(4)) - (0.0001 * faceValue * random.nextInt(3)) + random.nextDouble();
			String factorString = String.format("%.2f", factor);
			factor = Double.parseDouble(factorString);
			marketPrice.setMarketPrice(faceValue + factor);
			marketPriceRepository.save(marketPrice);
		}
		
	}

	@Transactional
	public void insertRandomTrades() {
		
		Random random = new Random();
		int numberOfTrades = 50 + random.nextInt(25);
		Iterable<MasterSecurityDTO> masterSecurityDTOList = masterSecurityService.getMasterSecuritiesDTOList();
		ArrayList<MasterSecurityDTO> masterSecList = new ArrayList();
		for (MasterSecurityDTO security : masterSecurityDTOList) {
			masterSecList.add(security);
		}
		while(numberOfTrades > 0) {
			int randomIndex = 0 + random.nextInt(12);
			MasterSecurityDTO securityConsidered = masterSecList.get(randomIndex);
			Trade trade = new Trade();
			trade.setBuy(random.nextBoolean());
			if(trade.isBuy()) {
				trade.setQuantity(10 + random.nextInt(500));
			}
			else {
				trade.setQuantity(10 + random.nextInt(200));
			}
			
			Date issuedDate = securityConsidered.getIssueDate();
			Date maturityDate = securityConsidered.getMaturityDate();
			
			//Date finalDate = getRandomFinalDate(issuedDate, maturityDate);
			Date finalDate = new Date();
			trade.setTradeDate(finalDate);
			double faceValue = securityConsidered.getFaceValue();
			double factor = (0.00001 * faceValue * random.nextInt(4)) - (0.00001 * faceValue * random.nextInt(3)) + random.nextDouble();
			String factorString = String.format("%.2f", factor);
			factor = Double.parseDouble(factorString);
			trade.setPrice(faceValue + factor);
			
			trade.setMasterSecurityId(securityConsidered.getIsin());
			
			tradeRepository.save(trade);
			numberOfTrades--;
		}
	}

	public Date getRandomFinalDate(Date issuedDate, Date maturityDate) {
		Random random = new Random();
		Date startDate = new Date("2020-4-1"); 
		Date endDate = new Date("2021-3-31");
		Date extremeLeft = new Date();
		Date extremeRight = new Date();
		if(startDate.before(issuedDate)) {
			extremeLeft = issuedDate;
		}
		else {
			extremeLeft = startDate;
		}
		if(endDate.after(maturityDate)) {
			extremeRight = maturityDate;
		}
		else {
			extremeRight = endDate;
		}
			
		long interval = extremeRight.getTime() - extremeLeft.getTime();
		long finalInterval = extremeLeft.getTime() + (long)random.nextDouble() * interval;
		Date finalDate = new Date(finalInterval);
		return finalDate;
		
	}
	
	

}
