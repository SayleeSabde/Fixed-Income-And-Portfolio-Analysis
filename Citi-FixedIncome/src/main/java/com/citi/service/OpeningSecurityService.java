package com.citi.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.controller.OpeningSecurityController;
import com.citi.dto.MasterSecurityDTO;
import com.citi.dto.OpeningFundsDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.entity.MarketPrice;
import com.citi.entity.MasterSecurity;
import com.citi.entity.OpeningSecurity;
import com.citi.repository.MarketPriceRepository;
import com.citi.repository.MasterSecurityRepository;
import com.citi.repository.OpeningSecurityRepository;

@Service
public class OpeningSecurityService {
	
	static Logger logger = LoggerFactory.getLogger(OpeningSecurityService.class);
	
	@Autowired
	OpeningSecurityRepository openingSecurityRepository;
	
	@Autowired 
	MasterSecurityService masterSecurityService;
	
	public MasterSecurityService getMasterSecurityService() {
		return masterSecurityService;
	}

	public void setMasterSecurityService(MasterSecurityService masterSecurityService) {
		this.masterSecurityService = masterSecurityService;
	}
	@Autowired 
	MasterSecurityRepository masterSecurityRepository;
	
	@Autowired
	MarketPriceRepository marketPriceRepository;

	public MarketPriceRepository getMarketPriceRepository() {
		return marketPriceRepository;
	}

	public void setMarketPriceRepository(MarketPriceRepository marketPriceRepository) {
		this.marketPriceRepository = marketPriceRepository;
	}

	public MasterSecurityRepository getMasterSecurityRepository() {
		return masterSecurityRepository;
	}

	public void setMasterSecurityRepository(MasterSecurityRepository masterSecurityRepository) {
		this.masterSecurityRepository = masterSecurityRepository;
	}

	public OpeningSecurityRepository getOpeningSecurityRepository() {
		return openingSecurityRepository;
	}

	public void setOpeningSecurityRepository(OpeningSecurityRepository openingSecurityRepository) {
		this.openingSecurityRepository = openingSecurityRepository;
	}
	
	@Transactional
	public Iterable<OpeningSecurityDTO> getOpeningSecurity() throws ParseException {
		
		openingSecurityRepository.deleteAll();
		insertRandomOpeningSecurity();
		return getOpeningSecurityDTOList();
	}
	
	public List<OpeningSecurityDTO> getOpeningSecurityDTOList() {
		Iterable<OpeningSecurity> openingSecurity = openingSecurityRepository.findAll();
		List<OpeningSecurityDTO> openingSecurityList = new ArrayList<>();
		for(OpeningSecurity security : openingSecurity) {
			OpeningSecurityDTO openingSecurityDTO = new OpeningSecurityDTO();
			openingSecurityDTO.setIsin(security.getIsin());
			openingSecurityDTO.setIssuerName(security.getIssuerName());
			openingSecurityDTO.setSecurity(security.getSecurity());
			openingSecurityDTO.setQuantity(security.getQuantity());
			openingSecurityDTO.setMaturityDate(security.getMaturityDate());
			openingSecurityDTO.setBuyDate(security.getBuyDate());
			openingSecurityDTO.setBuyPrice(security.getBuyPrice());
			openingSecurityDTO.setCouponRate(security.getCouponRate());
			openingSecurityList.add(openingSecurityDTO);
		}
		return openingSecurityList;
		
	}
	@Transactional
	private void insertRandomOpeningSecurity() throws ParseException {
		
		Random random = new Random();
		int numberOfSecurities = 7 + random.nextInt(2);
		Iterable<MasterSecurityDTO> masterSecurityDTOList = masterSecurityService.getMasterSecuritiesDTOList();
		ArrayList<MasterSecurityDTO> masterSecList = new ArrayList();
		for (MasterSecurityDTO security : masterSecurityDTOList) {
			masterSecList.add(security);
		}
//		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
//		ArrayList<MasterSecurity> masterSecList = new ArrayList();
//		for (MasterSecurity security : masterSecurityList) {
//			masterSecList.add(security);
//		}
		int securitiesCount = (int) masterSecurityRepository.count();
		//Iterable<MarketPrice> marketPrice = marketPriceRepository.findAll();
		while(numberOfSecurities > 0) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			int randomIndex = random.nextInt(securitiesCount);
			MasterSecurityDTO masterSecurity = masterSecList.get(randomIndex);
			Date issuedate = masterSecurity.getIssueDate();
			if(checkIssueDate(issuedate) == "invalidBuy") {
				masterSecList.remove(randomIndex);
				numberOfSecurities--;
				securitiesCount--;
				continue;
			}
			OpeningSecurity openingSecurity = new OpeningSecurity();
			String isin = masterSecurity.getIsin();
			openingSecurity.setIsin(isin);
			openingSecurity.setIssuerName(masterSecurity.getIssuerName());
			openingSecurity.setSecurity(masterSecurity.getSecurity());
			openingSecurity.setQuantity(100 + random.nextInt(100));
			//String maturityDate = masterSecurityRepository.findById(isin).get().getMaturityDate();
			Date maturityDate = masterSecurity.getMaturityDate();
			System.out.println(maturityDate);
			openingSecurity.setMaturityDate(format.format(maturityDate));
			openingSecurity.setCouponRate(masterSecurity.getCouponRate());
			Date issueDate = masterSecurity.getIssueDate();
			String randomDate = generateRandomDate(issueDate, maturityDate);
			openingSecurity.setBuyDate(randomDate);
			double faceValue = masterSecurity.getFaceValue();
			double buyPrice = generateBuyPrice(faceValue);
			openingSecurity.setBuyPrice(buyPrice);
			openingSecurity.setOpeningFunds(500000000 + 4500000000.0 * random.nextDouble());
			openingSecurityRepository.save(openingSecurity);
			masterSecList.remove(randomIndex);        //Removing the master security with index randomIndex from masterSecList
			numberOfSecurities--;
			securitiesCount--;
		}
		
	}
	
	private String generateRandomDate(Date issueDate, Date maturityDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String startdate = "2020-04-01";
		Date startDate = format.parse(startdate);
		if(startDate.after(maturityDate)) {
			startDate = maturityDate;
		}
		long start = issueDate.getTime();
		long end = startDate.getTime();
		long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end);
		//long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
	    Date finaldate = new Date(randomEpochDay);
	    String finalDate = format.format(finaldate);
		return finalDate;
	}
	
	private double generateBuyPrice(double faceValue) {
		Random random = new Random();
		double factor = (0.0001 * faceValue * random.nextInt(4)) - (0.0001 * faceValue * random.nextInt(3)) + random.nextDouble();
		String factorString = String.format("%.2f", factor);
		factor = Double.parseDouble(factorString);
		return faceValue + factor;
		
	}
	
	private String checkIssueDate(Date issueDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String startdate = "2020-04-01";
		Date startDate = format.parse(startdate);
		if(issueDate.after(startDate))  {
			return "invalidBuy";
		}
		else {
			return "validBuy";
		}
	}
	
	public OpeningFundsDTO getOpeningFunds() {
		OpeningFundsDTO openingFundsDTO = new OpeningFundsDTO();
		Iterable<OpeningSecurity> openingSecurityList = openingSecurityRepository.findAll();
		ArrayList<OpeningSecurity> openingSecList = new ArrayList();
		for (OpeningSecurity security : openingSecurityList) {
			openingSecList.add(security);
			break;
		}
		openingFundsDTO.setOpeningFunds(openingSecList.get(0).getOpeningFunds());
		return openingFundsDTO;
	}
	
	
}
