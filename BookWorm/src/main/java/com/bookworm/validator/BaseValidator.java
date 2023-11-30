package com.bookworm.validator;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookworm.conf.security.UserService;
import com.bookworm.dao.BookDao;
import com.bookworm.error.BookWormException;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.OrderRepository;
import com.bookworm.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
abstract class BaseValidator {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected BookDao bookDao;
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	protected void validateRequest(Object request) {

		if (request == null) {
			log.error("Error during validation of request. Details: Request cannot be null.");
			throw new BookWormException("Request cannot be null.");
		}

	}
	
	protected void validateBookId(ObjectId bookId) {

		if (bookDao.findById(bookId).isEmpty()) {
			throw new BookWormException("Book not found.");
		}

	}
	
	protected void validateUserId(ObjectId userId) throws BookWormException {

		if (userRespository.findById(userId).isEmpty()) {
			throw new BookWormException("User not found.");
		}

	}
	
	protected void validateCustomerId(ObjectId customerId) throws BookWormException {

		if(customerRepository.findById(customerId).isEmpty()) {
			log.error("Error during validation of customer orders retrieval request. Details: CustomerId is not valid.");
			throw new BookWormException("Customer not found.");
		}

	}
	
	protected void validateOrderId(ObjectId orderId) throws BookWormException {

		if(orderRepository.findById(orderId).isEmpty()) {
			log.error("Error during validation of statistics request. Details: Order cannot be found.");
			throw new BookWormException("Order not found.");
		}

	}

}
