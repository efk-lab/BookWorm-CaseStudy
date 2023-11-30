package com.bookworm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookworm.document.Customer;
import com.bookworm.document.Order;
import com.bookworm.error.BookWormException;
import com.bookworm.mapper.CustomerMapper;
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.CustomerValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService extends BaseService {

	@Autowired
	private CustomerValidator customerValidator;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private CustomerRepository customerRespository;
	
	@Autowired
	private OrderRepository orderRespository;
	

	public SaveCustomerResponse saveCustomer(SaveCustomerRequest saveCustomerRequest) throws BookWormException {

		Customer customer = null;
		SaveCustomerResponse saveCustomerResponse = null;
		Customer savedCustomer = null;

		customerValidator.validateSaveCustomerRequest(saveCustomerRequest);
		customer = customerMapper.toCustomer(saveCustomerRequest);
		savedCustomer = customerRespository.save(customer);
		saveCustomerResponse = customerMapper.toSaveCustomerResponse(savedCustomer);
		
		log.info("Customer saved. SaveCustomerResponse:" + saveCustomerResponse.toString() + " User:" + userService.getUser());
		
		return saveCustomerResponse;
		

	}

	public Page<GetCustomerOrdersResponse> getOrdersByCustomer(GetCustomerOrdersRequest getCustomerOrdersRequest, Pageable pageable) throws BookWormException {

		Page<GetCustomerOrdersResponse> getCustomerOrdersResponse = null;

		customerValidator.validateGetCustomerOrdersRequest(getCustomerOrdersRequest);
		Page<Order> result = orderRespository.findAllByCustomerCustomerId(getCustomerOrdersRequest.getCustomerId(), pageable);
		getCustomerOrdersResponse = customerMapper.toGetCustomerOrdersResponse(result);
		
		log.info("Customer orders retrieved. Customer Id:" + getCustomerOrdersRequest.getCustomerId());
		
		return getCustomerOrdersResponse;

	}
}
