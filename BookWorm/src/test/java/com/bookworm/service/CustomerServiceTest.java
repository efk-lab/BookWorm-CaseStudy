package com.bookworm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bookworm.conf.security.UserService;
import com.bookworm.document.Customer;
import com.bookworm.document.Order;
import com.bookworm.mapper.CustomerMapper;
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.CustomerValidator;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerValidator customerValidator;

	@Mock
	private CustomerMapper customerMapper;

	@Mock
	private CustomerRepository customerRespository;
	
	@Mock
	private OrderRepository orderRespository;
	
	@Mock
	private UserService userService;

	@InjectMocks
	private CustomerService customerService;
	

	@Test
	public void testSaveCustomer() throws Exception {

		SaveCustomerRequest saveCustomerRequest = new SaveCustomerRequest(); 
		SaveCustomerResponse saveCustomerResponseExpected = SaveCustomerResponse.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).customerFullName("xyz").build();
		Customer customer = Customer.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).fullName("xyz").build();

		doNothing().when(customerValidator).validateSaveCustomerRequest(saveCustomerRequest);
		given(customerMapper.toCustomer(any(SaveCustomerRequest.class))).willReturn(customer);
		given(customerRespository.save(any(Customer.class))).willReturn(customer);
		given(customerMapper.toSaveCustomerResponse(any(Customer.class))).willReturn(saveCustomerResponseExpected);

		SaveCustomerResponse saveCustomerResponseActual = customerService.saveCustomer(saveCustomerRequest);

		assertThat(saveCustomerResponseActual.getCustomerFullName()).isEqualTo(saveCustomerResponseExpected.getCustomerFullName());

	}

	@Test
	public void testGetOrdersByCustomer() throws Exception {

		GetCustomerOrdersRequest getCustomerOrdersRequest = new GetCustomerOrdersRequest();
		getCustomerOrdersRequest.setCustomerId(new ObjectId("62d322ddf9f5e01864bed242"));
		List<Order> customerOrders = new ArrayList<Order>();
		Customer customer = Customer.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).fullName("xyz").build(); 
		Order order = Order.builder().orderId(new ObjectId("64f357297b58bd33770d34ec")).customer(customer).orderAmount(1000).build();
		customerOrders.add(order);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Order> pageOrdersExpected = new PageImpl<Order>(customerOrders, pageable, 1);
		

		List<GetCustomerOrdersResponse> customerOrdersResponse = new ArrayList<GetCustomerOrdersResponse>();
		GetCustomerOrdersResponse getCustomerOrdersResponse = GetCustomerOrdersResponse.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		customerOrdersResponse.add(getCustomerOrdersResponse);
		Page<GetCustomerOrdersResponse> pageOrdersResponseExpected = new PageImpl<GetCustomerOrdersResponse>(customerOrdersResponse, pageable, 1);
		
	
		
		doNothing().when(customerValidator).validateGetCustomerOrdersRequest(getCustomerOrdersRequest);
		given(orderRespository.findAllByCustomerCustomerId(any(ObjectId.class), any(Pageable.class))).willReturn(pageOrdersExpected);
		given(customerMapper.toGetCustomerOrdersResponse(any(Page.class))).willReturn(pageOrdersResponseExpected);

		Page<GetCustomerOrdersResponse> getCustomerOrdersResponseActual = customerService.getOrdersByCustomer(getCustomerOrdersRequest, pageable);

		assertThat(getCustomerOrdersResponseActual.getContent().get(0).getOrderAmount()).isEqualTo(pageOrdersResponseExpected.getContent().get(0).getOrderAmount());

	}

}