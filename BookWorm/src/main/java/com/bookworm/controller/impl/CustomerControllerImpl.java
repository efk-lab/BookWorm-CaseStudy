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

import com.bookworm.controller.CustomerController;
import com.bookworm.error.BookWormException;
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;
import com.bookworm.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerControllerImpl extends BaseControllerImpl implements CustomerController {

	@Autowired
	private CustomerService customerService;
	

	@Override
	public ResponseEntity<SaveCustomerResponse> saveCustomer(SaveCustomerRequest saveCustomerRequest) throws BookWormException {
		
		log.info("SaveCustomerRequest received: " + saveCustomerRequest.toString() + " RequestedBy: " + userService.getUser());

		SaveCustomerResponse saveCustomerResponse = customerService.saveCustomer(saveCustomerRequest);

		if (saveCustomerResponse != null) {
			return new ResponseEntity<>(saveCustomerResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveCustomerResponse, HttpStatus.NO_CONTENT);
		}

	}

	@Override
	public ResponseEntity<Page<GetCustomerOrdersResponse>> getCustomerOrders(GetCustomerOrdersRequest getCustomerOrdersRequest, int pageNumber, int pageSize) throws BookWormException {
		
		log.info("GetCustomerOrdersRequest received: " + getCustomerOrdersRequest.toString() + " RequestedBy: " + userService.getUser());
		
		List<GetCustomerOrdersResponse> orders = new ArrayList<GetCustomerOrdersResponse>();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<GetCustomerOrdersResponse> pageOrders = customerService.getOrdersByCustomer(getCustomerOrdersRequest, pageable);
		orders = pageOrders.getContent();

		if (orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(pageOrders, HttpStatus.OK);
		}
		
	}

}
