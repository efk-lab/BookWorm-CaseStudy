package com.bookworm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.document.Customer;
import com.bookworm.document.Order;
import com.bookworm.mapper.OrderMapper;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.OrderValidator;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	private OrderValidator orderValidator;

	@Mock
	private OrderMapper orderMapper;

	@Mock
	private OrderRepository orderRespository;
	
	@Mock
	private BookDao bookDao;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private OrderService orderService;
	
	@Test
	public void testSaveOrder() throws Exception {

		SaveOrderRequest saveOrderRequest = new SaveOrderRequest(); 
		SaveOrderResponse saveOrderResponseExpected = SaveOrderResponse.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).customerFullName("xyz").build();
		
		Customer customer = Customer.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).fullName("xyz").build();
		Order order = Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).customer(customer).build();
		Book book = Book.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookIsbn("0-19-853453").build();

		doNothing().when(orderValidator).validateSaveOrderRequest(saveOrderRequest);
		given(orderMapper.toOrder(any(SaveOrderRequest.class))).willReturn(order);
		given(orderRespository.save(any(Order.class))).willReturn(order);
		given(orderMapper.toBook(any(Order.class))).willReturn(book);
		given(bookDao.save(any(Book.class))).willReturn(book);
		given(orderMapper.toSaveOrderResponse(any(Order.class))).willReturn(saveOrderResponseExpected);

		SaveOrderResponse saveOrderResponseActual = orderService.saveOrder(saveOrderRequest);

		assertThat(saveOrderResponseActual.getCustomerFullName()).isEqualTo(saveOrderResponseExpected.getCustomerFullName());

	}


	@Test
	public void testGetOrder() throws Exception {

		GetOrderRequest getOrderRequest = new GetOrderRequest(); 
		getOrderRequest.setOrderId(new ObjectId("62d322ddf9f5e01864bed242"));
		GetOrderResponse getOrderResponseExpected = GetOrderResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		Customer customer = Customer.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).fullName("xyz").build();
		Optional<Order> order = Optional.of(Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).customer(customer).build());

		doNothing().when(orderValidator).validateGetOrderRequest(getOrderRequest);
		given(orderRespository.findById(any(ObjectId.class))).willReturn(order);
		given(orderMapper.toGetOrderResponse(any(Order.class))).willReturn(getOrderResponseExpected);

		GetOrderResponse getOrderResponseActual = orderService.getOrder(getOrderRequest);

		assertThat(getOrderResponseActual.getOrderAmount()).isEqualTo(getOrderResponseExpected.getOrderAmount());

	}
	
	@Test
	public void testGetOrders() throws Exception {

		GetOrdersRequest getOrdersRequest = new GetOrdersRequest(); 
		getOrdersRequest.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2023"));
		getOrdersRequest.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse("01/11/2023"));
		Customer customer = Customer.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).fullName("abc").build();
		Order order = Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).customer(customer).build();
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		Pageable pageable = PageRequest.of(1, 1);
		Page<Order> pageOrdersExpected = new PageImpl<Order>(orderList, pageable, 1);
		

		List<GetOrdersResponse> orderResponseList = new ArrayList<GetOrdersResponse>();
		GetOrdersResponse getOrdersResponse = GetOrdersResponse.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).orderAmount(1000).bookName("xyz").build();
		orderResponseList.add(getOrdersResponse);
		Page<GetOrdersResponse> pageOrdersResponseExpected = new PageImpl<GetOrdersResponse>(orderResponseList, pageable, 1);
		

		doNothing().when(orderValidator).validateGetOrdersRequest(getOrdersRequest);
		given(orderRespository.findOrders(any(Date.class), any(Date.class), any(Pageable.class))).willReturn(pageOrdersExpected);
		given(orderMapper.toGetOrdersResponse(any(PageImpl.class))).willReturn(pageOrdersResponseExpected);

		Page<GetOrdersResponse> getOrderResponseActual = orderService.getOrders(getOrdersRequest, pageable);

		assertThat(getOrderResponseActual.getContent().get(0).getOrderAmount()).isEqualTo(pageOrdersResponseExpected.getContent().get(0).getOrderAmount());

	}

}
