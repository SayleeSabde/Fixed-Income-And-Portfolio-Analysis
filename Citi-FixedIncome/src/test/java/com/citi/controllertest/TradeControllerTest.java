/**
 * 
 */
package com.citi.controllertest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;


//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.citi.dto.PostTradeDTO;
import com.citi.main.CitiFixedIncomeApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(classes = CitiFixedIncomeApplication.class)
@AutoConfigureMockMvc
class TradeControllerTest {
	
	


   
	
	@Autowired
	public MockMvc mvc;
	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
    }

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	@Test  
	public void getAllTrades() throws Exception {
		String uri = "/demo/all";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	@Test  
	public void viewAllTrades() throws Exception {
		//getAllTrades();
		String uri = "/demo/viewAll";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test   
	public void GetTradeDTO() throws Exception {
		//viewAllTrades();
		
		String uri = "/demo/GenerateTrade";
		PostTradeDTO postTradeDTO = new PostTradeDTO();
		postTradeDTO.setBuy(true);
		postTradeDTO.setIsin("IN0020060019");
		postTradeDTO.setPrice(12345);
		postTradeDTO.setQuantity(10);
		Date date = new Date();
		postTradeDTO.setTradeDate(date);
		String inputJson = mapToJson(postTradeDTO);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
				//mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		// assertEquals(201, status);
	}

}
