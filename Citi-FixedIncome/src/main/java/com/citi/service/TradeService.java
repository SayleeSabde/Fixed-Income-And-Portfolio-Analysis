package com.citi.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.controller.TradeController;
import com.citi.dto.GetTradeDTO;
import com.citi.dto.MasterSecurityDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.dto.PostTradeDTO;
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
	
	static Logger logger = LoggerFactory.getLogger(TradeController.class);
	
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
	
	@Autowired
	OpeningSecurityService openingSecurityService;
	
	@Autowired
	OpeningFundsService openingFundsService;
	
	public MasterSecurityRepository getMasterSecurityRepository() {
		return masterSecurityRepository;
	}

	public void setMasterSecurityRepository(MasterSecurityRepository masterSecurityRepository) {
		this.masterSecurityRepository = masterSecurityRepository;
	}

	public MasterSecurityService getMasterSecurityService() {
		return masterSecurityService;
	}

	public void setMasterSecurityService(MasterSecurityService masterSecurityService) {
		this.masterSecurityService = masterSecurityService;
	}

	public CouponInfoRepository getCouponInfoRepository() {
		return couponInfoRepository;
	}

	public void setCouponInfoRepository(CouponInfoRepository couponInfoRepository) {
		this.couponInfoRepository = couponInfoRepository;
	}

	public OpeningSecurityService getOpeningSecurityService() {
		return openingSecurityService;
	}

	public void setOpeningSecurityService(OpeningSecurityService openingSecurityService) {
		this.openingSecurityService = openingSecurityService;
	}
	
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
	public Iterable<GetTradeDTO> generateNewTrades() throws ParseException {
		
		tradeRepository.deleteAll();
		openingSecurityService.getNewOpeningSecurity();
		openingFundsService.getNewOpeningFunds();
		insertRandomTrades();
		//insertLogicalTrades();
		generateMarketPrices();
		return getTradeDTOListFromTrade();
	}
	
	@Transactional
	public void insertRandomTrades() throws ParseException {
		
		Random random = new Random();
		HashMap<String, Double> TradeQuantityMap = new HashMap<>();
		HashMap<String, Date> PrevTradeDateMap = new HashMap<>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		int numberOfTrades = 50 + random.nextInt(25);
//		int numberOfTrades = 15 + random.nextInt(2);
		Iterable<MasterSecurityDTO> masterSecurityDTOList = masterSecurityService.getMasterSecuritiesDTOList();
		ArrayList<MasterSecurityDTO> masterSecList = new ArrayList<MasterSecurityDTO>();
		ArrayList<Security> zeroCouponSecurityList = new ArrayList<Security>();
		Iterable<OpeningSecurityDTO> openingSecurityDTOList = openingSecurityService.getOpeningSecurityDTOList();
		ArrayList<OpeningSecurityDTO> openingSecurityList = new ArrayList<OpeningSecurityDTO>();
		for(Security zeroCouponSecurity : Security.values()) {
			if(zeroCouponSecurity.equals(Security.Treasury_Bills) || zeroCouponSecurity.equals(Security.Commercial_Papers) || zeroCouponSecurity.equals(Security.Certificate_Of_Deposits)) {
				zeroCouponSecurityList.add(zeroCouponSecurity);
			}
		}
		for (MasterSecurityDTO security : masterSecurityDTOList) {
			masterSecList.add(security);
		}
		for (OpeningSecurityDTO security : openingSecurityDTOList) {
			openingSecurityList.add(security);
		}
		for(OpeningSecurityDTO security : openingSecurityList) {
			TradeQuantityMap.put(security.getIsin(), security.getQuantity());
			PrevTradeDateMap.put(security.getIsin(), security.getBuyDate());
		}
		logger.debug("++++++++++++++++++++++Debug PrevTradeDateMap++++++++++++++++++++++++++++++++++++++++++");
		for(String s : PrevTradeDateMap.keySet()) {
			System.out.println(PrevTradeDateMap.get(s));	
		}
		while(numberOfTrades > 0) {
			double tradeQuantity = 0, sellquantity = 0;
			int randomIndex = 0 + random.nextInt(12);
			MasterSecurityDTO securityConsidered = masterSecList.get(randomIndex);
			Trade trade = new Trade();
			String isin = securityConsidered.getIsin();
			logger.debug("++++++++++++++++++++++Debug Isin++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(isin);
			trade.setMasterSecurityId(isin);
			double faceValue = securityConsidered.getFaceValue();
			trade.setBuy(random.nextBoolean());
			Date issuedDate = securityConsidered.getIssueDate();
			Date maturityDate = securityConsidered.getMaturityDate();
			if(PrevTradeDateMap.containsKey(isin)) {
				Date finalDate = getRandomFinalDate(issuedDate, maturityDate, PrevTradeDateMap.get(isin));
				if(finalDate == null) {
					logger.debug("++++++++++++++++++++++Debug 1 April 2000++++++++++++++++++++++++++++++++++++++++++");
					continue;
				}
				trade.setTradeDate(finalDate);
			}
			else {
				String startdate = "2020-04-01";
				Date startDate = format.parse(startdate);
				Date finalDate = getRandomFinalDate(issuedDate, maturityDate, startDate);
				if(finalDate == null) {
					continue;
				}
				trade.setTradeDate(finalDate);
			}
			if(trade.isBuy()) {
				logger.debug("++++++++++++++++++++++Inside Buy ++++++++++++++++++++++++++++++++++++++++++");
				trade.setQuantity(200 + random.nextInt(200));
				if(TradeQuantityMap.containsKey(isin)) {
					tradeQuantity = TradeQuantityMap.get(isin) + trade.getQuantity();
					TradeQuantityMap.put(isin, tradeQuantity);
				}
				else {
					TradeQuantityMap.put(isin, (double)trade.getQuantity());
				}	
			}
			else {
				if(TradeQuantityMap.containsKey(isin)) {
					tradeQuantity = TradeQuantityMap.get(isin);
				}
				logger.debug("++++++++++++++++++++++Debug TradeQuantity++++++++++++++++++++++++++++++++++++++++++");
				System.out.println(tradeQuantity);
				if(tradeQuantity > 0) {
					if(tradeQuantity >= 100) {
						sellquantity = 80 + random.nextInt((int)(tradeQuantity - 80));
					}
					else {
						continue;
					}
					trade.setQuantity((int)sellquantity); 
					tradeQuantity -= trade.getQuantity();
					if(tradeQuantity > 0) {
						TradeQuantityMap.put(isin, tradeQuantity);
					}
					else {
						TradeQuantityMap.remove(isin);
					}
				} 
				else {
					continue;
				}
			}
			PrevTradeDateMap.put(isin, trade.getTradeDate());
			//Date finalDate = new Date();
			double factor = (0.01 * faceValue * random.nextInt(4)) - (0.01 * faceValue * random.nextInt(3)) + random.nextDouble();
			String factorString = String.format("%.2f", factor);
			factor = Double.parseDouble(factorString);
			if(securityConsidered.getSecurity().equals("Treasury_Bills") || securityConsidered.getSecurity().equals("Certificate_Of_Deposits") || securityConsidered.getSecurity().equals("Commercial_Papers")) {
				logger.debug("++++++++++++++++++++++Debug zero coupon bonds ++++++++++++++++++++++++++++++++++++++++++");
				factor = (0.01 * faceValue * random.nextInt(4))+ random.nextDouble();
				factorString = String.format("%.2f", factor);
				factor = Double.parseDouble(factorString);
				trade.setPrice(faceValue - factor);
			}
			else {
				trade.setPrice(faceValue + factor);
			}
			tradeRepository.save(trade);
			numberOfTrades--;
		}
	}
	
	public Date getRandomFinalDate(Date issuedDate, Date maturityDate, Date prevTradeDate) throws ParseException {
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
		if(startDate.getTime() < prevTradeDate.getTime()) {
			startDate = prevTradeDate;
			startDate.setTime(prevTradeDate.getTime() + 3600000);
		}
		if(startDate.getTime() > endDate.getTime()) {
			return null;
		}
		long start = startDate.getTime();
		long end = endDate.getTime();
		//long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
		long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end);
		Date finalDate = new Date(randomEpochDay);
		return finalDate;	
	}


	public void insertLogicalTrades() throws ParseException {
		Random random = new Random();
		//int numberOfTrades = 50 + random.nextInt(25);
		Iterable<MasterSecurityDTO> masterSecurityDTOList = masterSecurityService.getMasterSecuritiesDTOList();
		ArrayList<MasterSecurityDTO> masterSecList = new ArrayList<MasterSecurityDTO>();
		for (MasterSecurityDTO security : masterSecurityDTOList) {
			masterSecList.add(security);
		}
		int counter;
		int index = 0;
		while(index < 13) {
			MasterSecurityDTO masterSecurity = masterSecList.get(index);
			counter = 5;
			while(counter > 0) {
				Trade trade = new Trade();
				trade.setMasterSecurityId(masterSecurity.getIsin());
				double faceValue = masterSecurity.getFaceValue();
				double factor = 0.01;
				if(masterSecurity.getCouponRate() == 0) {
					factor = (0.01 * faceValue * random.nextInt(5)) + random.nextDouble();
				}
				else {
					factor = (0.01 * faceValue * random.nextInt(5)) - (0.01 * faceValue * random.nextInt(5)) - random.nextDouble();
				}
				String factorString = String.format("%.2f", factor);
				factor = Double.parseDouble(factorString);
				trade.setPrice(faceValue - factor);
				if (counter > 3) {
					trade.setBuy(true);
					Date accurateDate = getRandomAccurateBuyDate(masterSecurity.getIssueDate(), masterSecurity.getMaturityDate());
					trade.setTradeDate(accurateDate);
					trade.setQuantity(200 + random.nextInt(480));
				}
				else {
					trade.setBuy(random.nextBoolean());
					Date accurateDate = getRandomAccurateSellDate(masterSecurity.getIssueDate(), masterSecurity.getMaturityDate());
					trade.setTradeDate(accurateDate);
					trade.setQuantity(20 + random.nextInt(180));
	
				}
				if(tradeInWindow(trade.getTradeDate())) {
					trade.setBuy(true);
				}
				tradeRepository.save(trade);
				counter--;
			}
			index = index + 1;
	}
		
		
		
	}

	private boolean tradeInWindow(Date tradeDate) throws ParseException {
		String startdate = "2020-09-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(startdate);
		String enddate = "2020-10-08";
		Date endDate = format.parse(enddate);
		if(tradeDate.before(endDate) && tradeDate.after(startDate)) {
				return true;
		}
		return false;
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
	
	
	@Transactional
	public List<GetTradeDTO> getTradeDTOListFromTrade() {
		logger.info("++++++++++++++++++++++++++ In Trade Service +++++++++++++++++++++++++ ");
		Iterable<Trade> tradesList = tradeRepository.findAll();
		
		List<GetTradeDTO> finalTradeList = new ArrayList<>();

		for(Trade savedTrade : tradesList ) {
			GetTradeDTO getTradeDTO = new GetTradeDTO();
			getTradeDTO.setTradeId(savedTrade.getTradeId());
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTime(savedTrade.getTradeDate());
			getTradeDTO.setTradeDate(gcal);
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
		Collections.sort(finalTradeList, new Comparator<GetTradeDTO>() {
	        @Override
	        public int compare(GetTradeDTO object1, GetTradeDTO object2) {
	            return (int) (object1.getTradeDate().compareTo(object2.getTradeDate()));
	        }
	    });
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
			double factor = (0.01 * faceValue * random.nextInt(4)) - (0.01 * faceValue * random.nextInt(4)) + random.nextDouble();
			if(masterSecurity.getCouponRate() == 0.0) {
				factor = (0.01 * faceValue * random.nextInt(4)) + random.nextDouble();
			}
			String factorString = String.format("%.2f", factor);
			factor = Double.parseDouble(factorString);
			marketPrice.setMarketPrice(faceValue - factor);
			marketPriceRepository.save(marketPrice);
		}
		
	}
	
	
	
	public Date getRandomAccurateBuyDate(Date issuedDate, Date maturityDate) throws ParseException {
		String startdate = "2020-04-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(startdate);
		String enddate = "2020-10-08";
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
		
		public Date getRandomAccurateSellDate(Date issuedDate, Date maturityDate) throws ParseException {
			String startdate = "2020-09-24";
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
			//long randomEpochDay = start;
		    Date finalDate = new Date(randomEpochDay);
			return finalDate;
}


//		public Date getRandomFinalDate(Date issuedDate, Date maturityDate) throws ParseException {
//			String startdate = "2020-04-01";
//			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//			Date startDate = format.parse(startdate);
//			String enddate = "2021-03-31";
//			Date endDate = format.parse(enddate);
//			if(issuedDate.after(startDate)) {
//				startDate = issuedDate; 
//			}
//			if(maturityDate.before(endDate)) {
//				endDate = maturityDate;
//			}
//
//			long start = startDate.getTime();
//			long end = endDate.getTime();
//			//long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
//			long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end);
//		    Date finalDate = new Date(randomEpochDay);
//			return finalDate;		
//	}
	
	public GetTradeDTO insertTrade(PostTradeDTO postTrade) {
		
		Trade savedTrade = new Trade();
		savedTrade.setBuy(postTrade.isBuy());
		savedTrade.setMasterSecurityId(postTrade.getIsin());
		savedTrade.setPrice(postTrade.getPrice());
		savedTrade.setQuantity(postTrade.getQuantity());
		//savedTrade.setTradeId(postTrade.getTradeId());
		savedTrade.setTradeDate(postTrade.getTradeDate());
		//trade.set => postTrade.get
		//savedTrade = rpo.save(trade)
		tradeRepository.save(savedTrade);
		GetTradeDTO getTradeDTO = new GetTradeDTO();
		getTradeDTO.setTradeId(savedTrade.getTradeId());
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(savedTrade.getTradeDate());
		getTradeDTO.setTradeDate(gcal);
		getTradeDTO.setPrice(savedTrade.getPrice());
		getTradeDTO.setQuantity(savedTrade.getQuantity());
		getTradeDTO.setBuy(savedTrade.isBuy());
		Optional<MasterSecurity> securityConsidered = masterSecurityRepository.findById(savedTrade.getMasterSecurityId());
		getTradeDTO.setSecurity(Security.valueOf(securityConsidered.get().getSecurity()));
		getTradeDTO.setIssuerName(securityConsidered.get().getIssuerName());
		getTradeDTO.setIsin(savedTrade.getMasterSecurityId());
//		getTradeDTO.setIssuerName(savedTrade.getIssuerName());
//		getTradeDTO.setSecurity(savedTrade.getSecurity());
		return getTradeDTO;
		
	}
	
	public double getMarketPriceByIsin(String isin) {
		MarketPrice marketPrice = marketPriceRepository.findById(isin).get();
		return marketPrice.getMarketPrice();
		
	}


}
