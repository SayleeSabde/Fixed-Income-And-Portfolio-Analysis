package com.citi.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.dto.OpeningFundsDTO;

@Service
public class OpeningFundsService {
	
	@Autowired
	OpeningFundsDTO openingFundsDTO;
	
	public OpeningFundsDTO getOpeningFunds() {
		Random random = new Random();
		Double funds = 50000000 + 4500000000.0 * random.nextDouble();
		String factorString = String.format("%.2f", funds);
		funds = Double.parseDouble(factorString);
		openingFundsDTO.openingFunds = funds; 
		return openingFundsDTO;
	}
	 
}
