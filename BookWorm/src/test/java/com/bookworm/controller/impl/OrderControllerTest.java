package com.bookworm.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bookworm.BookWormApplicationTest;
import com.bookworm.conf.security.UserService;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = {BookWormApplicationTest.class})
@WebMvcTest(OrderControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(OrderControllerImpl.class)
public class OrderControllerTest{
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	
	
	@Test
	public void testSaveOrder() throws Exception {
		SaveOrderRequest saveOrderRequest = new SaveOrderRequest();
		SaveOrderResponse saveOrderResponse = SaveOrderResponse.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).customerFullName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(orderService.saveOrder(any(SaveOrderRequest.class))).willReturn(saveOrderResponse);

		this.mockMvc.perform(post("/bookworm/order/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveOrderRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("customerFullName").value("xyz"));
	}
	
	@Test
	public void testGetOrder() throws Exception {
		GetOrderRequest getOrderRequest = new GetOrderRequest();
		GetOrderResponse getOrderResponse = GetOrderResponse.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).customerFullName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(orderService.getOrder(any(GetOrderRequest.class))).willReturn(getOrderResponse);

		this.mockMvc.perform(post("/bookworm/order/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getOrderRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("customerFullName").value("xyz"));
	}

	@Test
	public void testGetOrders() throws Exception {
		GetOrdersRequest getOrdersRequest = new GetOrdersRequest();
		List<GetOrdersResponse> orders = new ArrayList<GetOrdersResponse>();
		GetOrdersResponse getOrdersResponse = GetOrdersResponse.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		orders.add(getOrdersResponse);
		Pageable pageable = PageRequest.of(1, 1);
		Page<GetOrdersResponse> pageOrders = new PageImpl<GetOrdersResponse>(orders, pageable, 1);
		
		
		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(orderService.getOrders(any(GetOrdersRequest.class), any(Pageable.class))).willReturn(pageOrders);

		this.mockMvc.perform(post("/bookworm/order/all")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getOrdersRequest))
				.param("pageNumber", "1")
		        .param("pageSize", "1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content.[0].bookName").value("xyz"));
	}
	
}
