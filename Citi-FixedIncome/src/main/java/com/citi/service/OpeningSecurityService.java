package com.citi.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.dto.OpeningSecurityDTO;
import com.citi.entity.MarketPrice;
import com.citi.entity.MasterSecurity;
import com.citi.entity.OpeningSecurity;
import com.citi.repository.MarketPriceRepository;
import com.citi.repository.MasterSecurityRepository;
import com.citi.repository.OpeningSecurityRepository;

@Service
public class OpeningSecurityService {
	
	@Autowired
	OpeningSecurityRepository openingSecurityRepository;
	
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
	public Iterable<OpeningSecurityDTO> getOpeningSecurity() {
		
		openingSecurityRepository.deleteAll();
		insertRandomOpeningSecurity();
		return getOpeningSecurityDTOList();
	}
	
	private List<OpeningSecurityDTO> getOpeningSecurityDTOList() {
		Iterable<OpeningSecurity> openingSecurity = openingSecurityRepository.findAll();
		List<OpeningSecurityDTO> openingSecurityList = new ArrayList<>();
		for(OpeningSecurity security : openingSecurity) {
			OpeningSecurityDTO openingSecurityDTO = new OpeningSecurityDTO();
			openingSecurityDTO.setIsin(security.getIsin());
			openingSecurityDTO.setIssuerName(security.getIssuerName());
			openingSecurityDTO.setSecurity(security.getSecurity());
			openingSecurityDTO.setQuantity(security.getQuantity());
			openingSecurityDTO.setMaturityDate(security.getMaturityDate());
			openingSecurityDTO.setCouponRate(security.getCouponRate());
			openingSecurityList.add(openingSecurityDTO);
		}
		return openingSecurityList;
		
	}
	@Transactional
	private void insertRandomOpeningSecurity() {
		
		Random random = new Random();
		int numberOfSecurities = 4 + random.nextInt(4);
		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
		ArrayList<MasterSecurity> masterSecList = new ArrayList();
		for (MasterSecurity security : masterSecurityList) {
			masterSecList.add(security);
			
		}
		int securititesCount = (int) masterSecurityRepository.count();
		//Iterable<MarketPrice> marketPrice = marketPriceRepository.findAll();
		while(numberOfSecurities > 0) {
			int randomIndex = random.nextInt(securititesCount);
			MasterSecurity masterSecurity = masterSecList.get(randomIndex);
			OpeningSecurity openingSecurity = new OpeningSecurity();
			String isin = masterSecurity.getIsin();
			openingSecurity.setIsin(isin);
			openingSecurity.setIssuerName(masterSecurity.getIssuerName());
			openingSecurity.setSecurity(masterSecurity.getSecurity());
			openingSecurity.setQuantity(100 + random.nextInt(100));
			//openingSecurity.setPrice();
			String maturityDate = masterSecurityRepository.findById(isin).get().getMaturityDate();
			System.out.println(maturityDate);
			openingSecurity.setMaturityDate(maturityDate);
			openingSecurity.setCouponRate(masterSecurity.getCouponRate());
			openingSecurityRepository.save(openingSecurity);
			numberOfSecurities--;
		}
		
	}
	
	
}
