package com.citi.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.dto.BalanceDTO;
import com.citi.dto.CouponInfoDTO;
import com.citi.dto.GetTradeDTO;
import com.citi.dto.MasterSecurityDTO;
import com.citi.dto.OpeningSecurityDTO;
import com.citi.dto.PortfolioDTO;
import com.citi.dto.ProfitDTO;


@Service
public class SummaryService {
	@Autowired
	private TradeService tradeservice;
	@Autowired
	private MasterSecurityService mastersecurityservice;
	
	@Autowired
	private OpeningSecurityService oService ;

	@Autowired
	private CouponInfoService CouponInfoService;
	
	public void setoService(OpeningSecurityService oService) {
		this.oService = oService;
	}
	
	public void setTradeservice(TradeService tradeservice) {
		this.tradeservice = tradeservice;
	}

	public void setMastersecurityservice(MasterSecurityService mastersecurityservice) {
		this.mastersecurityservice = mastersecurityservice;
	}

	public void setCouponInfoService(CouponInfoService couponInfoService) {
		CouponInfoService = couponInfoService;
	}
	

	HashMap<String, Double> hmap_coupon = new HashMap<String,Double>();
	HashMap<String,Double> hmap_coupon_unrealised = new HashMap<String,Double>();
	HashMap<String,Double> hmap_quantity = new HashMap<String,Double>();
	
	static Logger logger = LoggerFactory.getLogger(SummaryService.class);
	//tradeservice.getTradeList();
	
	public double OpeningSecQuant(String isin) {
		
		logger.info("++++++++++++++++++++++++++ In OpeningSecQuant +++++++++++++++++++++++++ ");
		Iterable<OpeningSecurityDTO> openingSecurityList = oService.getOpeningSecurityDTOList();
		logger.error("LIST", openingSecurityList);
		HashMap<String,	Double> hmap_opening = new HashMap<String,Double>();
		double quant=0;
		for(OpeningSecurityDTO osd:openingSecurityList) {
			String isin2 = osd.getIsin();
			quant = osd.getQuantity();
			hmap_opening.put(isin2, quant);					
		}
		if(hmap_opening.containsKey(isin)) {
		quant = hmap_opening.get(isin);
		}
		else {
			quant=0;
		}
		return quant; 
	}
	
public double TradeQuant(String isin) {
		
		logger.info("++++++++++++++++++++++++++ In OpeningSecQuant +++++++++++++++++++++++++ ");
		List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
		//logger.error("LIST", openingSecurityList);
		HashMap<String,	Double> hmap_trade = new HashMap<String,Double>();
		double trade_quant=0;
		
		for(GetTradeDTO trade:tradeList) {
			double trade_quant_prev =0;
			double trade_quant_sum =0;
			double trade_quant_curr =0;
			String isin2 = trade.getIsin();
			trade_quant_curr = trade.getQuantity();
			if(hmap_trade.containsKey(isin2)) {
				trade_quant_prev = hmap_trade.get(isin2);
				}
			trade_quant_sum = trade_quant_curr + trade_quant_prev;
			hmap_trade.put(isin2, trade_quant_sum);					
		}
		if(hmap_trade.containsKey(isin)) {
		trade_quant = hmap_trade.get(isin);
		}
		
		
		return trade_quant; 
	}

	public double getFormQuant(Date input_date,String isin_form) {
		
		List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
			double openingbalance = 0;
				if(OpeningSecQuant(isin_form)>0) {
					openingbalance = OpeningSecQuant(isin_form);
				}
				double formquant=openingbalance;
		for(GetTradeDTO trade:tradeList) {
			String isin = trade.getIsin();
			GregorianCalendar d = trade.getTradeDate();
			Date dformat = d.getTime();
			if(dformat.before(input_date) && (isin.equals(isin_form))) {
				
				if(trade.isBuy()==true) {
					formquant = formquant + trade.getQuantity() ;
					}
					else{	
					formquant = formquant - trade.getQuantity();	
					}
				
			}
		}
		
		
		return formquant;
	}
	

	public double CalcCouponTot() throws ParseException {
		logger.info("++++++++++++++++++++++++++ IN COUPON TOT +++++++++++++++++++++++++ ");
		Iterable<CouponInfoDTO> couponinfolist = CouponInfoService.getCouponList();
		
		//List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
		double couponTot=0;
		logger.debug("Value of coupontot--->>>> "+ couponTot);
		for(CouponInfoDTO couponinfo_element: couponinfolist) {
			//logger.info("++++++++++++++++++++++++++ IN FOR LOOP +++++++++++++++++++++++++ ");
			//int id = m.getPid();
			String isin = couponinfo_element.getIsin();
			logger.debug("Value of isin---->>>> "+ isin);
			Date couponDate = couponinfo_element.getCouponDates();
			//logger.debug("Value of tradedate---->>>> "+ couponDate);
			double currentCoupon=0;
			double prevCoupon=0;
			double facevalue = couponinfo_element.getFaceValue();
			//logger.debug("Value of facevalue---->>>> "+ facevalue);
			//double facevalue = mastersecurityRepository.findById(isin).get().getFaceValue();
			double couponrate = couponinfo_element.getCouponRate();
			//logger.debug("Value of couponrate---->>>> "+ couponrate);
			//double dcc = CouponInfoService.getDccFactor(couponinfo_element.getDayCountConvention());
			//logger.debug("Value of total dcc---->>>> "+ dcc);
			boolean annual = couponinfo_element.isAnnual();
			//logger.debug("Value of annual---->>>> "+ annual);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
			Date d2 = df.parse("2021-03-31");
			if(couponDate.before(d2)) {
			currentCoupon = CalcCouponSec(isin,couponDate,facevalue,couponrate,annual);
			}
			logger.debug("Value of currentcoupon--->>>> "+ currentCoupon);
//			if(hmap_coupon.containsKey(isin)) {
//				prevCoupon = hmap_coupon.get(isin);
//			}
//			logger.debug("Value of prevcoupon---->>>> "+ prevCoupon);
			currentCoupon = currentCoupon + prevCoupon;
			logger.debug("Value of currentcoupon---->>>> "+ currentCoupon);
			hmap_coupon.put(isin, currentCoupon);
			couponTot = couponTot + currentCoupon;
			//logger.debug("Value of total coupon---->>>> "+ couponTot);
		}
		logger.debug("Value of total coupon---->>>> "+ couponTot);
		return couponTot;

	}
	
	public double CalcCouponTotByDate(Date formdate) {
		logger.info("++++++++++++++++++++++++++ IN COUPON TOT +++++++++++++++++++++++++ ");
		Iterable<CouponInfoDTO> couponinfolist = CouponInfoService.getCouponList();
		
		//List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
		double couponTot=0;
		logger.debug("Value of coupontot--->>>> "+ couponTot);
		for(CouponInfoDTO couponinfo_element: couponinfolist) {
			if(couponinfo_element.getCouponDates().before(formdate)) {
			//logger.info("++++++++++++++++++++++++++ IN FOR LOOP +++++++++++++++++++++++++ ");
			//int id = m.getPid();
			String isin = couponinfo_element.getIsin();
			logger.debug("Value of isin---->>>> "+ isin);
			Date couponDate = couponinfo_element.getCouponDates();
			//logger.debug("Value of tradedate---->>>> "+ couponDate);
			double currentCoupon=0;
			double prevCoupon=0;
			double facevalue = couponinfo_element.getFaceValue();
			//logger.debug("Value of facevalue---->>>> "+ facevalue);
			//double facevalue = mastersecurityRepository.findById(isin).get().getFaceValue();
			double couponrate = couponinfo_element.getCouponRate();
			//logger.debug("Value of couponrate---->>>> "+ couponrate);
			//double dcc = CouponInfoService.getDccFactor(couponinfo_element.getDayCountConvention());
			//logger.debug("Value of total dcc---->>>> "+ dcc);
			boolean annual = couponinfo_element.isAnnual();
			//logger.debug("Value of annual---->>>> "+ annual);
			currentCoupon = CalcCouponSec(isin,couponDate,facevalue,couponrate,annual);
			logger.debug("Value of currentcoupon--->>>> "+ currentCoupon);
//			if(hmap_coupon.containsKey(isin)) {
//				prevCoupon = hmap_coupon.get(isin);
//			}
//			logger.debug("Value of prevcoupon---->>>> "+ prevCoupon);
			currentCoupon = currentCoupon + prevCoupon;
			logger.debug("Value of currentcoupon---->>>> "+ currentCoupon);
			hmap_coupon.put(isin, currentCoupon);
			couponTot = couponTot + currentCoupon;
			//logger.debug("Value of total coupon---->>>> "+ couponTot);
		}
		}
		logger.debug("Value of total coupon---->>>> "+ couponTot);
		return couponTot;

	}
	
	public double couponSecByisin(String isin) throws ParseException {
		double couponbysec;
		//double d = CalcCouponTot();
		 if(hmap_coupon.containsKey(isin)) {
			 couponbysec = hmap_coupon.get(isin);
			 logger.debug("Value of total couponbysec---->>>> "+ couponbysec); 
		 }
		 else {
			 couponbysec = 0;
		 } 
		return couponbysec;
	}
	
	public double getTradeQuant(String isin,Date couponDate) {
		
		List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
		HashMap<String,Double> hmap_quantity_local = new HashMap<String,Double>();
		
		
		double quantfinal=0;
		for(GetTradeDTO t: tradeList) {
			//couponDate = mastersecurity.getCouponDate;
			double quant=0;
		
			String isin2 = t.getIsin();
			if(isin2.equals(isin)) {
			if(hmap_quantity_local.containsKey(isin2)) {
			quant = hmap_quantity_local.get(isin2);
			}
			GregorianCalendar d = t.getTradeDate();
			Date dformat = d.getTime();
			if( dformat.before(couponDate)==true) {
				if(t.isBuy()==true) {
				quant = quant + t.getQuantity() ;
				}
				else{	
				quant = quant - t.getQuantity();	
				}	
				
			hmap_quantity.put(isin2, quant);
			hmap_quantity_local.put(isin2, quant);
		}
			
			
		}
		}
			if(hmap_quantity_local.containsKey(isin)) {
			quantfinal = hmap_quantity_local.get(isin);	
			}
		
		return quantfinal;
		
	}
	public double CalcCouponSec(String isin2,Date couponDate
						,double faceValue,double couponRate,boolean annual) {
		
		logger.info("++++++++++++++++++++++++++ In CalcCouponSec +++++++++++++++++++++++++ ");
		
		double coupon=0;
		double quant;
		quant = getTradeQuant(isin2, couponDate);
		logger.debug("Value of quantity---->>>> "+ quant);
		logger.debug("Value of isin---->>>> "+ isin2);
//		if(hmap_quantity.containsKey(isin2)) {
//		quant = hmap_quantity.get(isin2);
//		}
		double openingsecquant =  OpeningSecQuant(isin2);
		//logger.debug("Value of opensecq---->>>> "+ openingsecquant);
		quant = quant + openingsecquant;
		
		//logger.debug("Value of quantity---->>>> "+ quant);
		if(annual) {
		coupon = faceValue*couponRate*0.01*quant;  //remove dcc
		}else {
		coupon = faceValue*couponRate*0.01*quant*0.5; //changed dcc
		}
		//logger.debug("Value of currcoupon---->>>> "+ coupon);
		
		return coupon;
		
		
	}
	
	
	public double fundsByDate(Date formdate) throws ParseException {
		//getting the data from database
		//TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade() ;
	   //Changed datatype in mastersecurityservice
		List<MasterSecurityDTO> masterSecurityList = mastersecurityservice.getMasterSecuritiesDTOList();;
		
		
		double initialBalance  = (oService.getOpeningFunds()).getOpeningFunds();
		//logger.debug("Value of openingfunds--->>>> "+ initialBalance);

		double ans = 0;
		double buyTrades = 0;
		double sellTrades = 0;
		double tradesPrice = 0;
		double tradesQuantity= 0;
		
		double coupon = 0;
		Date maturedDate;
		double maturedGlobal=0;
		
		for(GetTradeDTO data : tradeData ) {
		GregorianCalendar d = data.getTradeDate();
		Date date = d.getTime();
		if(date.before(formdate)) {
				tradesPrice = data.getPrice();
			tradesQuantity = data.getQuantity();
			if(data.isBuy()) {
				buyTrades = buyTrades + (tradesPrice*tradesQuantity);
			}else {
				sellTrades = sellTrades + (tradesPrice*tradesQuantity);
			}
			
		}
		}
		
		coupon = CalcCouponTotByDate(formdate);
		logger.debug("Value of coupontot---->>>> "+ coupon);
		for(MasterSecurityDTO m: masterSecurityList) {
			maturedDate = m.getMaturityDate();
			double matured=0;
			double matured_facevalue=0;
			
 //		Date d1 = df.parse(maturedDate);
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
//			Date d2 = df.parse("2021-03-31");
		    //Date 1 occurs before Date 2
		    if(maturedDate.before(formdate)) {
		    	//principle value
		    	String isin = m.getIsin();
		    	double q =0;
		    	matured_facevalue= m.getFaceValue();
//		    	for(GetTradeDTO x : tradeData) {
//		    		if(isin.equals(x.getIsin())) {
//		    			q = q + x.getQuantity(); 
//		    		}
//		    	}
		    	q = getFormQuant(maturedDate, isin); //call getformquant by date and isin
		    	logger.debug("Value of final quant---->>>> "+ q);
		    	matured= matured_facevalue*q;
		    	logger.debug("Value of matured---->>>> "+ matured);
		    }
		    maturedGlobal= maturedGlobal + matured;
		    logger.debug("Value of maturedfGlobal---->>>> "+ maturedGlobal);
		         
		}
		
		ans = initialBalance-buyTrades+sellTrades +coupon+maturedGlobal;
		logger.debug("Value of ans---->>>> "+ ans);
		
		return ans;
	}
	
	public double finalfunds() throws ParseException{
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
		Date d2 = df.parse("2021-03-31");
		
		return fundsByDate(d2);
		
	}
	
//	public double finalfunds() throws ParseException {
//		//getting the data from database
//		//TradeService t = new TradeService();
//		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade() ;
//	   //Changed datatype in mastersecurityservice
//		List<MasterSecurityDTO> masterSecurityList = mastersecurityservice.getMasterSecuritiesDTOList();;
//		
//		
//		double initialBalance  = (oService.getOpeningFunds()).getOpeningFunds();
//		
//
//		double ans = 0;
//		double buyTrades = 0;
//		double sellTrades = 0;
//		double tradesPrice = 0;
//		double tradesQuantity= 0;
//		
//		double coupon = 0;
//		Date maturedDate;
//		double maturedGlobal=0;
//		
//		for(GetTradeDTO data : tradeData ) {
//		 
//			tradesPrice = data.getPrice();
//			tradesQuantity = data.getQuantity();
//			if(data.isBuy()) {
//				buyTrades = buyTrades + (tradesPrice*tradesQuantity);
//			}else {
//				sellTrades = sellTrades + (tradesPrice*tradesQuantity);
//			}
//			
//		
//		}
//		
//		coupon = CalcCouponTot();
//		logger.debug("Value of coupontot---->>>> "+ coupon);
//		for(MasterSecurityDTO m: masterSecurityList) {
//			maturedDate = m.getMaturityDate();
//			double matured=0;
//			double matured_facevalue=0;
//			
// //		Date d1 = df.parse(maturedDate);
//			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");//we have to check the format of the date
//			Date d2 = df.parse("2021/03/31");
//		    //Date 1 occurs before Date 2
//		    if(maturedDate.before(d2)) {
//		    	//principle value
//		    	String isin = m.getIsin();
//		    	double q =0;
//		    	matured_facevalue= m.getFaceValue();
////		    	for(GetTradeDTO x : tradeData) {
////		    		if(isin.equals(x.getIsin())) {
////		    			q = q + x.getQuantity(); 
////		    		}
////		    	}
//		    	q = finalSec(isin);
//		    	//logger.debug("Value of final quant---->>>> "+ q);
//		    	matured= matured_facevalue*q;
//		    	//logger.debug("Value of matured---->>>> "+ matured);
//		    }
//		    maturedGlobal= maturedGlobal + matured;
//		    
//		         
//		}
//		
//		ans = initialBalance-buyTrades+sellTrades +coupon+maturedGlobal;
//		logger.debug("Value of buytrades---->>>> "+ buyTrades);
//		logger.debug("Value of coupon---->>>> "+ coupon);
//		logger.debug("Value of selltrades---->>>> "+ sellTrades);
//		logger.debug("Value of maturedfGlobal---->>>> "+ maturedGlobal);
//		logger.debug("Value of openingfunds--->>>> "+ initialBalance);
//		logger.debug("Value of ans---->>>> "+ ans);
//		return ans;
//	}
//	
//	public double finalfunds() throws ParseException {
//		
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
//		Date d2 = df.parse("2021-03-31");
//		
//		double finalfunds = fundsByDate(d2);
//		
//		return finalfunds;
//		
//	}
	
	
	public HashMap<String, Double> finalSec_hmap() {
		//TradeService t = new TradeService();
		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade() ;
		
		HashMap<String, Double> hmap = new HashMap<String,Double>();
		
		//OpeningSecurityService s = new OpeningSecurityService();
		Iterable<OpeningSecurityDTO> opening = oService.getOpeningSecurityDTOList();
//		ListIterator<OpeningSecurityDTO> listIterator = (ListIterator<OpeningSecurityDTO>) opening.iterator();
//		
//		while(listIterator.hasNext()) {
//		    hmap.put(listIterator.next().getIsin(), listIterator.next().getQuantity());
//		}
		for(OpeningSecurityDTO osd:opening) {
			String isin = osd.getIsin();
			double quant = osd.getQuantity();
			hmap.put(isin, quant);
		}
		double buyTrades = 0;
		double sellTrades = 0;
		double tradesQuantity= 0;
		double update =0;
		
		for(GetTradeDTO data : tradeData ) {
			
			tradesQuantity = data.getQuantity();
			String isin = data.getIsin();
			if(data.isBuy()) {
				buyTrades = tradesQuantity;
				if(hmap.containsKey(isin))
				{
				update = hmap.get(isin);
				}else {
					update=0;
				}
				update =update + buyTrades;
				//logger.debug("Value of update after buy---->>>> "+ update);
				hmap.put(data.getIsin(), update);
				
			}else {
				sellTrades =  tradesQuantity;
				if(hmap.containsKey(isin))
				{
				update = hmap.get(data.getIsin());
				}else {
					update=0;
				}
				update= update - sellTrades;
				//logger.debug("Value of update after sell---->>>> "+ update);
				hmap.put(data.getIsin(), update);
			}
			
		}
		return hmap;
	}
	
	
	public double finalSec(String isin) {
		
		HashMap<String, Double> hmap = new HashMap<String,Double>();
		hmap = finalSec_hmap();

		double finalquant = 0;
		if(!(isin.equals("IN0020190295") || isin.equals("INF760K01JG7") || isin.equals("INF158A01026")
				||isin.equals("INF03LN01013")|| isin.equals("INF021A01026")))
		{
			if(hmap.containsKey(isin)) {
				finalquant = hmap.get(isin);
			}
		}		
		return finalquant;
		
		
	}
	
	public double realisedProfitsTot() {
		
		
		//MasterSecurityService masterSecurityService = new MasterSecurityService();
		List<MasterSecurityDTO> master = mastersecurityservice.getMasterSecuritiesDTOList();
		
		double totalProfit = 0;
		
		for(MasterSecurityDTO m: master) {
			
			String isin = m.getIsin();
			double currentProfit = realisedProfitSecNew(isin);
			logger.debug("Value of currentprofit--->>>> "+ currentProfit);
			if(currentProfit>0 || currentProfit<0) {
			totalProfit = totalProfit + currentProfit ;
			}
			logger.debug("Value of totalprofit--->>>> "+ totalProfit);
		}

		return totalProfit;
	}

//	public double realisedProfitSec(String i) {
//		//TradeService t = new TradeService();
//		
//		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade();
//		
//		double realised_pnl = 0;
//		double buyTrades = 0;
//		double sellTrades = 0;
//		double tradesPrice = 0;
//		double tradesQuantity= 0;
//		double totalBuy = 0;
//		double totalSell = 0;
//		int f = 0;
//		
//		for(GetTradeDTO data : tradeData ){
//			
//			
//			String isin = data.getIsin();
//			if(isin.equals(i)) {
//				f=1;
//			logger.debug("++++++++++++++++++++++IN REALISEDPROFITSEC INIF++++++++++++++++++++++++++++++++++++++++++");
//				tradesPrice = data.getPrice();
//				logger.debug("Value of price--->>>>"+ tradesPrice);
//				tradesQuantity = data.getQuantity();
//				logger.debug("Value of quantity--->>>> "+ tradesQuantity);
//				if(data.isBuy()==true) {
//					logger.debug("++++++++++++++++++++++INIF BUY++++++++++++++++++++++++++++++++++++++++++");
//					buyTrades = buyTrades + (tradesPrice*tradesQuantity);
//					//logger.debug("Value of buytrades--->>>> "+ buyTrades);
//					totalBuy = totalBuy + tradesQuantity ;
//				}else {
//					sellTrades = sellTrades + (tradesPrice*tradesQuantity);
//					//logger.debug("Value of selltrades--->>>> "+ sellTrades);			
//					totalSell = totalSell + tradesQuantity;
//				}
//			}
//			
//		}
//		logger.debug("Value of buytrades--->>>> "+ buyTrades);
//		logger.debug("Value of selltrades--->>>> "+ sellTrades);
//		logger.debug("Value of totalsell--->>>> "+ totalSell);
//		logger.debug("Value of totalbuy--->>>> "+ totalBuy);
//		if(totalSell>0 && totalBuy>0) {
//		realised_pnl = ((sellTrades/totalSell)-(buyTrades/totalBuy))*totalSell;
//		}
//		if(f==0) {
//		realised_pnl = 0;
//		}
//		logger.debug("Value of ans--->>>> "+ realised_pnl);
//		return realised_pnl;
//	}
	
	public double realisedProfitSecNew(String i) {
		//TradeService t = new TradeService();
		
		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade();
		Iterable<OpeningSecurityDTO> openingSecurityList = oService.getOpeningSecurityDTOList();
		double openingPrice=0;
		double openingQuantity=0;
		for(OpeningSecurityDTO osd: openingSecurityList) {
			String isin2=osd.getIsin();
			if(isin2.equals(i))
			{
				openingPrice=osd.getBuyPrice();
				openingQuantity = osd.getQuantity();
			}
				
		}
		
		double tradesPrice = 0;
		double tradesQuantity= 0;
//		double avgbuyprice = openfundingservice.
		double avgBuyPrice=openingPrice;
		double totalQuantity=openingQuantity;
		double currProfit=0;
		double profitTotal=0;
		for(GetTradeDTO data : tradeData ){
			
			String isin = data.getIsin();
			logger.debug("Value of isin--->>>>"+ isin);
			if(isin.equals(i)) {
			logger.debug("++++++++++++++++++++++IN REALISEDPROFITSEC INIF++++++++++++++++++++++++++++++++++++++++++");
				tradesPrice = data.getPrice();
				logger.debug("Value of price--->>>>"+ tradesPrice);
				tradesQuantity = data.getQuantity();
				logger.debug("Value of quantity--->>>> "+ tradesQuantity);
				if(data.isBuy()==true) {
					avgBuyPrice=(avgBuyPrice*totalQuantity+tradesQuantity*tradesPrice)/(totalQuantity+tradesQuantity);
					logger.debug("Value of avgbuyprice--->>>> "+ avgBuyPrice);
					totalQuantity=totalQuantity+tradesQuantity;
					logger.debug("Value of totalquant if buy--->>>> "+ totalQuantity);
				}else {
					totalQuantity=totalQuantity-tradesQuantity;
					logger.debug("Value of totalquant if sell--->>>> "+ totalQuantity);
					currProfit=(tradesPrice-avgBuyPrice)*tradesQuantity;
					logger.debug("Value of currprofit--->>>> "+ currProfit);
					profitTotal=profitTotal+currProfit;
					logger.debug("Value of profittotal--->>>> "+ profitTotal);

				}
			}
			
		}

		return profitTotal;
	}
	
public double unrealisedCouponTot() throws ParseException{
		
		Iterable<CouponInfoDTO> master = CouponInfoService.getCouponList();
		
		
		double unrealisedcoupon = 0;
		
		for(CouponInfoDTO m: master) {
			
			String isin = m.getIsin();
			double currentcoupon_unrealised = unrealisedCouponSec(isin,m.getCouponDates(),m.getFaceValue(),
					m.getCouponRate(),m.isAnnual(),m.getDayCountConvention());
			hmap_coupon_unrealised.put(isin, currentcoupon_unrealised);
			
				unrealisedcoupon = unrealisedcoupon + currentcoupon_unrealised ;
				
		}
		

		return unrealisedcoupon;
	}

public double getTradeQuantAfterCoupon(String isin, Date couponDate) throws ParseException {
	List<GetTradeDTO> tradeList = tradeservice.getTradeDTOListFromTrade();
	HashMap<String,Double> hmap_quantity_unrealised = new HashMap<String,Double>();
	
	
	
	double quantfinal=0;
	for(GetTradeDTO t: tradeList) {
		//couponDate = mastersecurity.getCouponDate;
		double quant=0;
		double quant2=0;
		String isin2 = t.getIsin();
		if(isin2.equals(isin)) {
		if(hmap_quantity_unrealised.containsKey(isin2)) {
		quant = hmap_quantity_unrealised.get(isin2);
		}
		GregorianCalendar d = t.getTradeDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//we have to check the format of the date
		Date d2 = df.parse("2021-03-31");
		Date dformat = d.getTime();
		if( dformat.after(couponDate)==true && dformat.before(d2)==true) {
			
			if(t.isBuy()==true) {
			quant = quant + t.getQuantity() ;
			}
			else{	
			quant = quant - t.getQuantity();	
			}	
			
		hmap_quantity_unrealised.put(isin2, quant);			
	}
		else {
			quant2 = quant2 + t.getQuantity();
		}
		
	}
	}
		if(hmap_quantity_unrealised.containsKey(isin)) {
		quantfinal = hmap_quantity_unrealised.get(isin);	
		}
	
	return quantfinal;
	
	
}

public double unrealisedCouponSec(String isin,Date couponDate
		,double faceValue,double couponRate,boolean annual,String dcc) throws ParseException {
	
	
	double coupon=0;
	double quant;
	quant = getTradeQuantAfterCoupon(isin, couponDate);

	double openingsecquant = 0;
	if(hmap_quantity.containsKey(isin)) {
		//openingsecquant = hmap_quantity.get(isin);
		openingsecquant = getTradeQuant(isin, couponDate);
		if(OpeningSecQuant(isin)>0) {
			openingsecquant = openingsecquant + OpeningSecQuant(isin);
		}
	}
	quant = quant + openingsecquant;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date d2 = null;
	try {
		d2 = df.parse("2021-03-31");
	} catch (ParseException e) {
		e.printStackTrace();
	}
	 long difference_In_Time 
     = d2.getTime() - couponDate.getTime(); 
	 
	 long difference_In_Days 
     = (difference_In_Time 
        / (1000 * 60 * 60 * 24)) 
       % 365; 
	 
	 long difference_in_months = difference_In_Days/30;
	 double difference;
	 double dcc_double = CouponInfoService.getDccFactor(dcc);
	 String dccSplit[] = dcc.split("_");
		List<String> dccSplitList = new ArrayList<String>();
		dccSplitList = Arrays.asList(dccSplit);
		String n = dccSplitList.get(0);
		double numerator = 0;
		if(n.equals("30")) {
			numerator = 30;
		}
		else {
			numerator = 365;
		}
		if(numerator==30) {
			difference = difference_in_months;
		}else {
			
			difference  = difference_In_Days;
		}

	if(annual) {
		
		coupon = faceValue*couponRate*quant*0.01*(dcc_double)*difference;  
	}else {
		coupon = faceValue*couponRate*quant*0.5*0.01*(dcc_double)*difference; 
	}
	
	return coupon;

}

public double unrealisedcouponSecByisin(String isin) throws ParseException {
	double unrealisedcouponbysec;
	//double d = unrealisedCouponTot();
	 if(hmap_coupon_unrealised.containsKey(isin)) {
		 unrealisedcouponbysec = hmap_coupon_unrealised.get(isin);
		 logger.debug("Value of total couponbysec---->>>> "+ unrealisedcouponbysec); 
	 }
	 else {
		 unrealisedcouponbysec = 0;
	 } 
	return unrealisedcouponbysec;
}

public double unrealisedProfitTot(){
		
		List<MasterSecurityDTO> master = mastersecurityservice.getMasterSecuritiesDTOList();
		
		double unrealisedtotalProfit = 0;
		
		for(MasterSecurityDTO m: master) {
			
			String isin = m.getIsin();
			double currentProfit = unrealisedProfitSec(isin);
			logger.debug("Value of currentprofit--->>>> "+ currentProfit);
			if(currentProfit>0 || currentProfit<0) {
				unrealisedtotalProfit = unrealisedtotalProfit + currentProfit ;
			}
			logger.debug("Value of totalprofit--->>>> "+ unrealisedtotalProfit);
		}

		return unrealisedtotalProfit;
	}

	
	public double unrealisedProfitSec(String i) {
		List<GetTradeDTO> tradeData= tradeservice.getTradeDTOListFromTrade();
		Iterable<OpeningSecurityDTO> openingSecurityList = oService.getOpeningSecurityDTOList();
		double openingPrice=0;
		double openingQuantity=0;
		for(OpeningSecurityDTO osd: openingSecurityList) {
			String isin2=osd.getIsin();
			if(isin2.equals(i))
			{
				openingPrice=osd.getBuyPrice();
				openingQuantity = osd.getQuantity();
			}
				
		}
		
		double tradesPrice = 0;
		double tradesQuantity= 0;
//		double avgbuyprice = openfundingservice.
		double avgBuyPrice=openingPrice;
		double totalQuantity=openingQuantity;
		double finalUnrealised=0;
		for(GetTradeDTO data : tradeData ){
			String isin = data.getIsin();
			double marketprice = tradeservice.getMarketPriceByIsin(isin);
			logger.debug("Value of isin--->>>>"+ isin);
			if(isin.equals(i)) {
			logger.debug("++++++++++++++++++++++IN REALISEDPROFITSEC INIF++++++++++++++++++++++++++++++++++++++++++");
				tradesPrice = data.getPrice();
				logger.debug("Value of price--->>>>"+ tradesPrice);
				tradesQuantity = data.getQuantity();
				logger.debug("Value of quantity--->>>> "+ tradesQuantity);
				if(data.isBuy()==true) {
					avgBuyPrice=(avgBuyPrice*totalQuantity+tradesQuantity*tradesPrice)/(totalQuantity+tradesQuantity);
					logger.debug("Value of avgbuyprice--->>>> "+ avgBuyPrice);
					totalQuantity=totalQuantity+tradesQuantity;
					logger.debug("Value of totalquant if buy--->>>> "+ totalQuantity);
				
			}
				else {
					totalQuantity=totalQuantity-tradesQuantity;
				}
				
				finalUnrealised=(marketprice-avgBuyPrice)*totalQuantity;		
			
		}

		}	
		return finalUnrealised;
		
}
	
	public double finalvaluationbyisin(String isin) {
		
		double marketprice = tradeservice.getMarketPriceByIsin(isin);
		double finalquant = finalSec(isin);
		
		
		double finalvaluation = finalquant*marketprice;
		
		return finalvaluation;
		
	}
	
	public TreeMap<Date, Double> fundsbydate_hmap() throws ParseException{
		
	       TreeMap<Date,Double> datemap = new TreeMap<Date,Double>();
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	       Date startDate = formatter.parse("2020-04-02");
	       Date endDate = formatter.parse("2021-04-07");
	       Calendar start = Calendar.getInstance();
	       start.setTime(startDate);
	       Calendar end = Calendar.getInstance();
	       end.setTime(endDate);
	       for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 7), date = start.getTime()) {
	    	   
	    	   double funds = fundsByDate(date);
	    	   datemap.put(date, funds);
	       }
		
		
		return datemap;
	}

	public List<ProfitDTO> getProfitLossDTOList() throws ParseException {
		List<MasterSecurityDTO> openingSecurity = mastersecurityservice.getMasterSecuritiesDTOList();
		List<ProfitDTO> profitdtolist = new ArrayList<>();
		for(MasterSecurityDTO security : openingSecurity) {
			
			ProfitDTO profitdto = new ProfitDTO();
			profitdto.setIsin(security.getIsin());
			profitdto.setIssuername(security.getIssuerName());
			profitdto.setCouponIncome(couponSecByisin(security.getIsin())); // pass the appropriate function
			profitdto.setUnrealisedcouponIncome(unrealisedcouponSecByisin(security.getIsin()));
			profitdto.setCapitalGainLoss(realisedProfitSecNew(security.getIsin()));  // pass the appropriate function
			profitdto.setUnrealisedProfitLoss(unrealisedProfitSec(security.getIsin()));
			profitdtolist.add(profitdto);

		}
		return profitdtolist;
	}
	
	public List<PortfolioDTO> getPortfolioDTOList() {
		List<MasterSecurityDTO> openingSecurity = mastersecurityservice.getMasterSecuritiesDTOList();
		List<PortfolioDTO> portfoliodtolist = new ArrayList<>();
		for(MasterSecurityDTO security : openingSecurity) {
			
			PortfolioDTO portfoliodto = new PortfolioDTO();
			portfoliodto.setIsin(security.getIsin());
			portfoliodto.setIssuername(security.getIssuerName());
			portfoliodto.setMaturityDate(security.getMaturityDate());
			portfoliodto.setBalance((int)finalSec(security.getIsin()));  // pass the appropriate function
			portfoliodto.setFinalValuation(finalvaluationbyisin(security.getIsin()));
			portfoliodtolist.add(portfoliodto);
			
		}
		return portfoliodtolist;
	}
	
	public BalanceDTO getBalanceDTOList() throws ParseException {
		//List<MasterSecurityDTO> openingSecurity = mastersecurityservice.getMasterSecuritiesDTOList();
		//st<BalanceDTO> balacedtolist = new ArrayList<>();
		
			BalanceDTO balanceto = new BalanceDTO();
			balanceto.setRealisedCoupon((CalcCouponTot()));  // pass the appropriate function
			balanceto.setUnrealisedcoupon(unrealisedCouponTot());
			balanceto.setRealisedYield(realisedProfitsTot());   // pass the appropriate function
			balanceto.setUnrealisedYield(unrealisedProfitTot());
		
		
		return balanceto;
	}

}
