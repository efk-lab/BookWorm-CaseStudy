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
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;
import com.bookworm.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ContextConfiguration(classes = {BookWormApplicationTest.class})
@WebMvcTest(CustomerControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(CustomerControllerImpl.class)
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	
	
	@Test
	public void testSaveCustomer() throws Exception {
		SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest();
		SaveCustomerResponse saveCustomerResponse = SaveCustomerResponse.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).customerFullName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(customerService.saveCustomer(any(SaveCustomerRequest.class))).willReturn(saveCustomerResponse);

		this.mockMvc.perform(post("/bookworm/customer/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveCustomerRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("customerFullName").value("xyz"));
		
	}
	
	@Test
	public void testGetCustomerOrders() throws Exception {
		GetCustomerOrdersRequest getCustomerOrdersRequest = new GetCustomerOrdersRequest();
		List<GetCustomerOrdersResponse> customerOrders = new ArrayList<GetCustomerOrdersResponse>();
		GetCustomerOrdersResponse getCustomerOrdersResponse = GetCustomerOrdersResponse.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		customerOrders.add(getCustomerOrdersResponse);
		Pageable pageable = PageRequest.of(1, 1);
		Page<GetCustomerOrdersResponse> pageOrders = new PageImpl<GetCustomerOrdersResponse>(customerOrders, pageable, 0);
		
		
		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(customerService.getOrdersByCustomer(any(GetCustomerOrdersRequest.class), any(Pageable.class))).willReturn(pageOrders);

		this.mockMvc.perform(post("/bookworm/customer/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getCustomerOrdersRequest))
				.param("pageNumber", "1")
		        .param("pageSize", "1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content.[0].bookName").value("xyz"));
	}

}
