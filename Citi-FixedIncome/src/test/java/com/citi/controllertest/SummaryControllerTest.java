/**
 * 
 */
package com.citi.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.citi.main.CitiFixedIncomeApplication;


@SpringBootTest(classes = CitiFixedIncomeApplication.class)
@AutoConfigureMockMvc
public class SummaryControllerTest {
//	
	@Autowired
	public MockMvc mvc;
////	@Test
////	void test() {
////		fail("Not yet implemented");
////	}
	@Test
	public void OpeningSecQuant() throws Exception {
		String uri = "/demo1/opensecquan";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void getTradeQuan() throws Exception {
		String uri = "/demo1/tradequan1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void CalcCouponSec() throws Exception {
		String uri = "/demo1/couponsec";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void CalcCouponTot() throws Exception {
		String uri = "/demo1/coupontot";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void unrealisedCouponTot() throws Exception {
		String uri = "/demo1/unrealisedcouponsec";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void realisedProfitSec() throws Exception {
		String uri = "/demo1/realisedprofitsecnew";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void finalfunds() throws Exception {
		String uri = "/demo1/finalfunds";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void finalsec() throws Exception {
		String uri = "/demo1/finalsec";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void couponSecByisin() throws Exception {
		String uri = "/demo1/couponbyisin";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void getprofitlist() throws Exception {
		String uri = "/demo1/profitlist";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void getPortfolioDTOList() throws Exception {
		String uri = "/demo1/portfoliolist";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void unrealisedProfitSec() throws Exception {
		String uri = "/demo1/unrealisedprofitsecnew";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void unrealisedProfitsTot() throws Exception {
		String uri = "/demo1/unrealisedprofittot";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void fundsByDate() throws Exception {
		String uri = "/demo1/fundsbydate";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).queryParam("date", "2020-08-24")).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void getFormQuant() throws Exception {

		String uri = "/demo1/formquant";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).queryParam("date", "2020-08-24").queryParam("isin", "IN0020060019")).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
}
