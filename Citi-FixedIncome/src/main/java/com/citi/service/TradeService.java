/**
 * 
 */
package com.citi.service;

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
import com.citi.entity.Security;
import com.citi.entity.Trade;
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
	
	public TradeRepository getTradeRepository() {
		return tradeRepository;
	}

	public void setTradeRepository(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	@Transactional
	public Iterable<GetTradeDTO> generateNewTrades() {
		tradeRepository.deleteAll();
		insertRandomTrades();
		
		logger.info("++++++++++++++++++++++++++ In Trade Service +++++++++++++++++++++++++ ");
		Iterable<Trade> tradesList = tradeRepository.findAll();
		GetTradeDTO getTradeDTO = new GetTradeDTO();
		List<GetTradeDTO> finalTradeList = new ArrayList<>();

		for(Trade savedTrade : tradesList ) {
			
			getTradeDTO.setTradeId(savedTrade.getTradeId());
			getTradeDTO.setTradeDate(savedTrade.getTradeDate());
			getTradeDTO.setPrice(savedTrade.getPrice());
			getTradeDTO.setQuantity(savedTrade.getQuantity());
			getTradeDTO.setBuy(savedTrade.isBuy());
			//To depend on MasterSecurity
			getTradeDTO.setSecurity(Security.Treasury_Bill);
			getTradeDTO.setIsin("ISIN12345678");
			getTradeDTO.setIssuerName("issuerName");
			finalTradeList.add(getTradeDTO);
		}
		return finalTradeList;
	}

	private void insertRandomTrades() {
		
		Random random = new Random();
		int numberOfTrades = 50 + random.nextInt(25);
		
		while(numberOfTrades > 0) {
			Trade trade = new Trade();
			trade.setQuantity(10);
			trade.setTradeDate(new Date());
			tradeRepository.save(trade);
			numberOfTrades--;
		}
	}

}
