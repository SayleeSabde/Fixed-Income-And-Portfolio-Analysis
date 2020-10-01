package com.citi.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.dto.CouponInfoDTO;
import com.citi.dto.GetBalanceDTO;
import com.citi.dto.GetTradeDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.dto.PortfolioDTO;
import com.citi.dto.ProfitDTO;
import com.citi.service.CouponInfoService;
//import com.citi.dto.GetTradeDTO;
import com.citi.service.SummaryService;
@RestController
@CrossOrigin
@RequestMapping("/demo1")
public class SummaryController {

	@Autowired
	private CouponInfoService CouponInfoService;
	
	@Autowired
	SummaryService summaryservice;
	
	static Logger logger = LoggerFactory.getLogger(SummaryController.class);
	
	
	@RequestMapping(method = RequestMethod.GET, path="/opensecquan")
	public @ResponseBody double OpeningSecQuant()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		//Date d = new Date();
		//return summaryservice.CalcCouponSec("INF021A01026", d, 1, 50000, 5.8, true);
		return summaryservice.OpeningSecQuant("INF956O01016");
	}
	
//	@RequestMapping(method = RequestMethod.GET, path="/tradequan")
//	public @ResponseBody double TradeQuan()  {
//		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
//		//Date d = new Date();
//		//return summaryservice.CalcCouponSec("INF021A01026", d, 1, 50000, 5.8, true);
//		return summaryservice.TradeQuant("INF204KB13C7");
//	}
	
	@RequestMapping(method = RequestMethod.GET, path="/tradequan1")
	public @ResponseBody double getTradeQuan()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		Date d = new Date();
		//return summaryservice.CalcCouponSec("INF021A01026", d, 1, 50000, 5.8, true);
		return summaryservice.getTradeQuant("INF204KB13C7", d);
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/couponsec")
	public @ResponseBody double CalcCouponSec() throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
		Date d2 = df.parse("2020-11-27");
		return summaryservice.CalcCouponSec("INF758T01015", d2, 10000, 9, true);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/coupontot")
	public @ResponseBody double CalcCouponTot()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.CalcCouponTot();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/unrealisedcoupontot")
	public @ResponseBody double unrealisedCouponTot() throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.unrealisedCouponTot();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/unrealisedcouponsec")
	public @ResponseBody double unrealisedCouponSec() throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
		Date d2 = df.parse("2020-11-27");//""INF758T01015""
		return summaryservice.unrealisedCouponSec("INF758T01015", d2, 10000, 9, true, "30_360");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/realisedprofitsecnew")
	public @ResponseBody double realisedProfitSec()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.realisedProfitSecNew("INF03LN01013");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/realisedprofittot")
	public @ResponseBody double realisedProfitsTot()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.realisedProfitsTot();
		
	}
	

	@RequestMapping(method = RequestMethod.GET, path="/finalfunds")
	public @ResponseBody double finalfunds() throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		return summaryservice.finalfunds();
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path="/finalsec")
	public @ResponseBody double finalsec() {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.finalSec("INF204KB13C7");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/couponbyisin")
	public @ResponseBody double couponSecByisin() {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.couponSecByisin("INF758T01015");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/profitlist")
	public @ResponseBody List<ProfitDTO> getprofitlist() throws ParseException {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.getProfitLossDTOList();
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/portfoliolist")
	public @ResponseBody List<PortfolioDTO> getPortfolioDTOList(){
		
		return summaryservice.getPortfolioDTOList();
	}
	@RequestMapping(method = RequestMethod.GET, path="/unrealisedprofitsecnew")
	public @ResponseBody double unrealisedProfitSec()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.unrealisedProfitSec("INF03LN01013");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/unrealisedprofittot")
	public @ResponseBody double unrealisedProfitsTot()  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
	
		return summaryservice.unrealisedProfitTot();
		
	}
	
	//Form Input Controllers
	
	@RequestMapping(method = RequestMethod.GET, path="/fundsbydate")
	public @ResponseBody double fundsByDate(@RequestBody String date) throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
		Date d = df.parse(date);
		return summaryservice.fundsByDate(d);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/formquant")
	public @ResponseBody double getFormQuant(@RequestBody GetBalanceDTO getBalance) throws ParseException  {
		logger.debug("++++++++++++++++++++++Debug++++++++++++++++++++++++++++++++++++++++++");
		Date d =  getBalance.getTradeDate();
		String isin = getBalance.getIsin();
		return summaryservice.getFormQuant(d, isin);
		
	}
	
}
