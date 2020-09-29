/**
 * 
 */
package com.citi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//import javax.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.dto.GetTradeDTO;
import com.citi.service.TradeService;

/**
 * @author Dhruv
 *
 */
@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class TradeController {
	
	static Logger logger = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
	TradeService tradeService;

	@RequestMapping(method = RequestMethod.GET, path="/all")
	public @ResponseBody Iterable<GetTradeDTO> getAllTrades() {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		return tradeService.generateNewTrades();
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/viewAll")
	public @ResponseBody Iterable<GetTradeDTO> viewAllTrades() {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		return tradeService.getTradeDTOListFromTrade();
	}
	
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/newTrades")
//	public @ResponseBody Iterable<GetTradeDTO> generateNewTrades() {
//		return tradeService.generateNewTrades();
//	}
//	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "{id}")
//	public @ResponseBody TradeDTO getTradeById(@PathVariable("id") String id) {
//		return tradeService.getTradeById(id);
//	}
//	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/viewTrades")
//	public @ResponseBody Iterable<TradeDTO> viewAllTrades() {
//		return tradeService.viewAllTrades();
//	}
	
	

}
