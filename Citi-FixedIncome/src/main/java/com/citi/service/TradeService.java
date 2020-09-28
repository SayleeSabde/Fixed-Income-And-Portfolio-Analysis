/**
 * 
 */
package com.citi.service;

import java.util.ArrayList;
import java.util.List;

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
	public Iterable<GetTradeDTO> dummyGet() {
		
//		Trade trade =new Trade();
//		trade.setQuantity(10);
//		tradeRepository.save(trade);
		
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
	
//	@Transactional
//	public GetTradeDTO dummySave() {
//		
//		logger.debug("+++++++++++++++++++++++In Trade Service++++++++++++++++++++++++++++++++");
//		
//		Trade trade =new Trade();
//		trade.setQuantity(10);
//		Trade savedTrade = tradeRepository.save(trade);
//		GetTradeDTO getTradeDTO = new GetTradeDTO();
//		getTradeDTO.setTradeId(savedTrade.getTradeId());
//		getTradeDTO.setTradeDate(savedTrade.getTradeDate());
//		getTradeDTO.setPrice(savedTrade.getPrice());
//		getTradeDTO.setQuantity(savedTrade.getQuantity());
//		getTradeDTO.setBuy(savedTrade.isBuy());
//		
//		//To depend on MasterSecurity
//		getTradeDTO.setSecurity(Security.Treasury_Bill);
//		getTradeDTO.setIsin("ISIN12345678");
//		getTradeDTO.setIssuerName("issuerName");
//		
//		return getTradeDTO;
		
//	}
	

}
