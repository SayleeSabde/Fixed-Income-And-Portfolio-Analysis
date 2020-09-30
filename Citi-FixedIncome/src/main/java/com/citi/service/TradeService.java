package com.citi.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.controller.TradeController;
import com.citi.dto.GetTradeDTO;
import com.citi.dto.MasterSecurityDTO;
import com.citi.dto.PostTradeDTO;
import com.citi.entity.CouponInfo;
import com.citi.entity.MarketPrice;
import com.citi.entity.MasterSecurity;
import com.citi.entity.Security;
import com.citi.entity.Trade;
import com.citi.repository.CouponInfoRepository;
import com.citi.repository.MarketPriceRepository;
import com.citi.repository.MasterSecurityRepository;
import com.citi.repository.TradeRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
				trade.setQuantity(110 + random.nextInt(500));
			}
			else {
				if(numberOfTrades > 30)  {
					trade.setQuantity(10 + random.nextInt(100));
					trade.setBuy(true);
				}
				else {
					trade.setQuantity(10 + random.nextInt(100));
				}
			}
			
			Date issuedDate = securityConsidered.getIssueDate();
			Date maturityDate = securityConsidered.getMaturityDate();
			Date finalDate = new Date();
			try {
				finalDate = getRandomFinalDate(issuedDate, maturityDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			trade.setTradeDate(finalDate);
			//trade.setTradeDate(date);
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
	
	public Date getRandomFinalDate(Date issuedDate, Date maturityDate) throws ParseException {
			String startdate = "2020-04-01";
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date startDate = format.parse(startdate);
			String enddate = "2021-03-31";
			Date endDate = format.parse(enddate);
			if(issuedDate.after(startDate)) {
				startDate = issuedDate; 
			}
			if(maturityDate.before(endDate)) {
				endDate = maturityDate;
			}

			long start = startDate.getTime();
			long end = endDate.getTime();
			//long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
			long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end);
		    Date finalDate = new Date(randomEpochDay);
			return finalDate;
	}
	
	public List<Date> getRandomFinalDates(int numberOfTrades) throws ParseException {
		List<Date> finalDates = new ArrayList<>();
		String startdate = "2020-04-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(startdate);
		String enddate = "2021-03-31";
		Date endDate = format.parse(enddate);
		long start = startDate.getTime();
		long end = endDate.getTime();
		//long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
		long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end);
	    Date finalDate = new Date(randomEpochDay);
		finalDates.add(finalDate);
		return finalDates;
}
	
	public GetTradeDTO insertTrade(PostTradeDTO postTrade) {
		
		Trade trade = new Trade();
		trade.setBuy(postTrade.isBuy());
		trade.setMasterSecurityId(postTrade.getIsin());
		trade.setPrice(postTrade.getPrice());
		trade.setQuantity(postTrade.getQuantity());
		trade.setTradeDate(postTrade.getTradeDate());
		
		Trade savedTrade = tradeRepository.save(trade);
		GetTradeDTO getTradeDTO = new GetTradeDTO();
		getTradeDTO.setTradeId(savedTrade.getTradeId());
		getTradeDTO.setTradeDate(savedTrade.getTradeDate());
		getTradeDTO.setPrice(savedTrade.getPrice());
		getTradeDTO.setQuantity(savedTrade.getQuantity());
		getTradeDTO.setBuy(savedTrade.isBuy());
		
		
		Optional<MasterSecurity> securityConsidered = masterSecurityRepository.findById(savedTrade.getMasterSecurityId());
		getTradeDTO.setSecurity(Security.valueOf(securityConsidered.get().getSecurity()));
		getTradeDTO.setIssuerName(securityConsidered.get().getIssuerName());
		String isin = savedTrade.getMasterSecurityId();
		getTradeDTO.setIsin(isin);
		return getTradeDTO;
		
	}	

}
