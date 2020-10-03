package com.citi.controllertest;

import static org.junit.jupiter.api.Assertions.*;

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
class OpeningSecurityControllerTest {
	
	@Autowired
	public MockMvc mvc;
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	@Test
	public void getOpeningSecurity() throws Exception {
		String uri = "/demo/openingsecurity";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test
	public void getOpeningFunds() throws Exception {
		String uri = "/demo/openingfunds";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

}
