package com.bookworm.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.document.Order;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderMapper extends BaseMapper {
	
	@Autowired
	private BookDao bookDao;

	@Autowired
	private CustomerRepository customerRepository;
	

	public Order toOrder(SaveOrderRequest saveOrderRequest) {
		
		log.info("Mapping SaveBookRequest to Book. SaveBookRequest:" + saveOrderRequest.toString() + " User:" + userService.getUser());

		return Order.builder()
				.bookCount(saveOrderRequest.getOrderCount())
				.orderAmount(saveOrderRequest.getOrderAmount())
				.customer(customerRepository.findById(saveOrderRequest.getCustomerId()).get())
				.book(bookDao.findById(saveOrderRequest.getBookId()).get())
				.orderDate(new Date())
				.build();

	}
	
	public SaveOrderResponse toSaveOrderResponse(Order order) {
		
		log.info("Mapping Order to SaveOrderResponse. order:" + order.toString() + " User:" + userService.getUser());
		
		SaveOrderResponse saveOrderResponse = SaveOrderResponse.builder()
				.orderId(order.getOrderId())
				.orderCount(order.getBookCount())
				.orderAmount(order.getOrderAmount())
				.customerFullName(order.getCustomer().getFullName())
				.bookName(order.getBook().getBookName())
				.bookId(order.getBook().getBookId())
				.orderDate(order.getOrderDate())
				.build();
		
		return (SaveOrderResponse)toBaseResponse(saveOrderResponse, order);

	}

	public GetOrderResponse toGetOrderResponse(Order order) {
		
		log.info("Mapping Order to GetOrderResponse. Order:" + order.toString() + " User:" + userService.getUser());
		
		GetOrderResponse getOrderResponse = GetOrderResponse.builder()
				.orderId(order.getOrderId())
				.orderCount(order.getBookCount())
				.orderAmount(order.getOrderAmount())
				.orderDate(order.getOrderDate())
				.customerId(order.getCustomer().getCustomerId())
				.customerFullName(order.getCustomer().getFullName())
				.customerEmail(order.getCustomer().getUser().getEmail())
				.bookId(order.getBook().getBookId())
				.bookName(order.getBook().getBookName())
				.build();

		return (GetOrderResponse)toBaseResponse(getOrderResponse, order);
		
	}

	
	public Page<GetOrdersResponse> toGetOrdersResponse(Page<Order> orderPage) {
		
		log.info("Mapping Order to GetCustomerOrdersResponse. OrderPage:" + orderPage.toString() + " User:" + userService.getUser());

		List<GetOrdersResponse> orderResultDtoList = new ArrayList<GetOrdersResponse>();
		List<Order> orders = orderPage.getContent();

		orders.stream().forEach(order -> orderResultDtoList.add(GetOrdersResponse.builder()
				.orderId(order.getOrderId())
				.orderCount(order.getBookCount())
				.orderAmount(order.getOrderAmount())
				.orderDate(order.getOrderDate())
				.customerId(order.getCustomer().getCustomerId())
				.customerFullName(order.getCustomer().getFullName())
				.customerEmail(order.getCustomer().getUser().getEmail())
				.bookId(order.getBook().getBookId())
				.bookName(order.getBook().getBookName())
				.build()));

        return new PageImpl<>(orderResultDtoList, orderPage.getPageable(), orderPage.getTotalElements());

	}


	
	public Book toBook(Order order) {

		log.info("Discounting order from book stock count. SaveBookRequest:" + order.toString() + " User:" + userService.getUser());
		
		Book book = order.getBook();
		book.setStockCount(book.getStockCount() - order.getBookCount());
		
		return book;

	}

}
