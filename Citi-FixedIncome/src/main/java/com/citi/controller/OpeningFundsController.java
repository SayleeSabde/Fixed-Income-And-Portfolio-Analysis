package com.citi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.dto.OpeningFundsDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.service.OpeningFundsService;
import com.citi.service.OpeningSecurityService;

@RestController // This means that this class is a Controller
@CrossOrigin
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class OpeningFundsController {
	
	static Logger logger = LoggerFactory.getLogger(OpeningFundsController.class);
	
	@Autowired
	OpeningFundsService openingFundsService;
	
	@RequestMapping(method = RequestMethod.GET, path="/openingfunds")
	public @ResponseBody OpeningFundsDTO getOpeningFunds() {
		logger.debug("++++++++++++++++++++++Debug Opening Funds++++++++++++++++++++++++++++++++++++++++++");
		return openingFundsService.getOpeningFunds();
	}
}
