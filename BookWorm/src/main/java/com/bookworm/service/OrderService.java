package com.bookworm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookworm.dao.BookDao;
import com.bookworm.document.Order;
import com.bookworm.error.BookWormException;
import com.bookworm.mapper.OrderMapper;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.OrderValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService extends BaseService {

	@Autowired
	private OrderValidator orderValidator;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderRepository orderRespository;
	
	@Autowired
	private BookDao bookDao;
	

	@Transactional
	public SaveOrderResponse saveOrder(SaveOrderRequest saveOrderRequest) throws BookWormException {

		Order order = null;
		Order savedOrder = null;
		SaveOrderResponse saveOrderResponse = null;

		orderValidator.validateSaveOrderRequest(saveOrderRequest);
		order = orderMapper.toOrder(saveOrderRequest);
		savedOrder = orderRespository.save(order);
		bookDao.save(orderMapper.toBook(savedOrder));
		saveOrderResponse = orderMapper.toSaveOrderResponse(savedOrder);

		log.info("Book saved. SaveOrderResponse: " + saveOrderResponse.toString() + " User:" + userService.getUser());

		return saveOrderResponse;

	}

	public GetOrderResponse getOrder(GetOrderRequest setOrderRequest) throws BookWormException {

		Optional<Order> retrievedOrder = null;
		GetOrderResponse getOrderResponse = null;

		orderValidator.validateGetOrderRequest(setOrderRequest);
		retrievedOrder = orderRespository.findById(setOrderRequest.getOrderId());
		getOrderResponse = orderMapper.toGetOrderResponse(retrievedOrder.get());
		
		log.info("Order retrieved. GetOrderResponse: " + getOrderResponse.toString() + " User:" + userService.getUser());

		return getOrderResponse;

	}

	public Page<GetOrdersResponse> getOrders(GetOrdersRequest getOrdersRequest, Pageable pageable) throws BookWormException {

		Page<GetOrdersResponse> getOrdersResponseList = null;

		orderValidator.validateGetOrdersRequest(getOrdersRequest);
		Page<Order> pageOrder = orderRespository.findOrders(getOrdersRequest.getStartDate(), getOrdersRequest.getEndDate(), pageable);
        getOrdersResponseList = orderMapper.toGetOrdersResponse(pageOrder);

		log.info("Orders listed. Start Date: " + getOrdersRequest.getStartDate() + " End Date:" + getOrdersRequest.getEndDate());
		
		return getOrdersResponseList;

	}

}
