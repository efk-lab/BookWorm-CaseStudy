package com.bookworm.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bookworm.controller.OrderController;
import com.bookworm.error.BookWormException;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderControllerImpl extends BaseControllerImpl implements OrderController {
	
	@Autowired
	private OrderService orderService;
	

	@Override
	public ResponseEntity<SaveOrderResponse> saveOrder(SaveOrderRequest saveOrderRequest) throws BookWormException {
		
		log.info("SaveOrderRequest received: " + saveOrderRequest.toString() + " RequestedBy: " + userService.getUser());
		
		SaveOrderResponse saveOrderResponse = orderService.saveOrder(saveOrderRequest);

		if (saveOrderResponse != null) {
			return new ResponseEntity<>(saveOrderResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveOrderResponse, HttpStatus.NO_CONTENT);
		}
		
	}

	@Override
	public ResponseEntity<GetOrderResponse> getOrder(GetOrderRequest getOrderRequest) throws BookWormException {
		
		log.info("GetOrderRequest received: " + getOrderRequest.toString() + " RequestedBy: " + userService.getUser());
		
		GetOrderResponse getOrderResponse = orderService.getOrder(getOrderRequest);

		if (getOrderResponse != null) {
			return new ResponseEntity<>(getOrderResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getOrderResponse, HttpStatus.NO_CONTENT);
		}
		
	}

	@Override
	public ResponseEntity<Page<GetOrdersResponse>> getOrders(GetOrdersRequest getOrdersRequest, int pageNumber, int pageSize) throws BookWormException {
		
		List<GetOrdersResponse> orders = new ArrayList<GetOrdersResponse>();
		
		log.info("GetOrdersRequest received: " + getOrdersRequest.toString() + " RequestedBy: " + userService.getUser());
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<GetOrdersResponse> pageOrders = orderService.getOrders(getOrdersRequest, pageable);
		orders = pageOrders.getContent();

		if (orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pageOrders, HttpStatus.OK);
		}
		
	}

	
}
