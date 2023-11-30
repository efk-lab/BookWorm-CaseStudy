package com.bookworm.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrderResponse;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.GetOrdersResponse;
import com.bookworm.model.SaveOrderRequest;
import com.bookworm.model.SaveOrderResponse;

@RequestMapping("/bookworm/order")
public interface OrderController {

	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<SaveOrderResponse> saveOrder(@Valid @RequestBody SaveOrderRequest saveOrderRequest) throws BookWormException;

	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public ResponseEntity<GetOrderResponse> getOrder(@Valid @RequestBody GetOrderRequest getOrderRequest) throws BookWormException;
	
	@RequestMapping(
			value = "/all", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public ResponseEntity<Page<GetOrdersResponse>> getOrders(@Valid @RequestBody GetOrdersRequest getOrdersRequest, @RequestParam int pageNumber,  @RequestParam int pageSize) throws BookWormException;

}
