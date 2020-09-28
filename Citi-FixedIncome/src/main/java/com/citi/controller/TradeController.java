/**
 * 
 */
package com.citi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.dto.GetTradeDTO;
import com.citi.service.TradeService;






/**
 * @author Dhruv
 *
 */
@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class TradeController {
	

	@Autowired
	TradeService tradeService;

	@PostMapping(path="/add", consumes = "application/json", produces = "application/json")
	public @ResponseBody GetTradeDTO addNewUser () {
		return tradeService.dummySave();
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<GetTradeDTO> getAllUsers() {
		// This returns a JSON or XML with the users
		return tradeService.dummyGet();
	}
	
	

}
