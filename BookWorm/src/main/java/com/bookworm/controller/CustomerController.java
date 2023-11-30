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
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;

@RequestMapping("/bookworm/customer")
public interface CustomerController {

	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public ResponseEntity<SaveCustomerResponse> saveCustomer(@Valid @RequestBody SaveCustomerRequest saveCustomerRequest) throws BookWormException;

	
	@RequestMapping(
			value = "/orders",
			method = RequestMethod.POST,
	        consumes = {MediaType.APPLICATION_JSON_VALUE},
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public ResponseEntity<Page<GetCustomerOrdersResponse>> getCustomerOrders(@Valid @RequestBody GetCustomerOrdersRequest getCustomerOrdersRequest,  @RequestParam int pageNumber, @RequestParam int pageSize) throws BookWormException;
}
