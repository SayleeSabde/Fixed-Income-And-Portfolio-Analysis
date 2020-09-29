/**
 *  
 */
package com.citi.controller;

//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.service.OrderService;

@RestController // This means that this class is a Controller
@CrossOrigin
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET, value = "/recentOrders")
//	public @ResponseBody Iterable<OrderDTO> getRecentOrders() {
//		return orderService.getRecentOrders();
//	}
//	
//	@RequestMapping(produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON, method = RequestMethod.POST, value = "/placeOrder")
//	public @ResponseBody void placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
//		return orderService.placeOrder(placeOrderDTO);
//	}
}