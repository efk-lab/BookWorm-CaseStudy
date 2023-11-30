package com.bookworm.validator;

import org.springframework.stereotype.Component;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetCustomerOrdersRequest;
import com.bookworm.model.SaveCustomerRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerValidator extends BaseValidator {
	
	public void validateSaveCustomerRequest(SaveCustomerRequest saveCustomerRequest) throws BookWormException {
		
		validateRequest(saveCustomerRequest);
		validateUserId(saveCustomerRequest.getUserId());
		
		log.info("SaveCustomerRequest validated. SaveCustomerRequest: " + saveCustomerRequest.toString() + " User:" + userService.getUser());
		
	}
	
	public void validateGetCustomerOrdersRequest(GetCustomerOrdersRequest getCustomerOrdersRequest) throws BookWormException {
		
		validateRequest(getCustomerOrdersRequest);
		validateCustomerId(getCustomerOrdersRequest.getCustomerId());
		
	}

}
