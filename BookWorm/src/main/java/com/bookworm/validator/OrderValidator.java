package com.bookworm.validator;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetOrderRequest;
import com.bookworm.model.GetOrdersRequest;
import com.bookworm.model.SaveOrderRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderValidator extends BaseValidator {

	public void validateSaveOrderRequest(SaveOrderRequest saveOrderRequest) throws BookWormException {

        validateRequest(saveOrderRequest);
        validateCustomerId(saveOrderRequest.getCustomerId());
        validateBookId(saveOrderRequest.getBookId());

	}
	
	public void validateGetOrderRequest(GetOrderRequest getOrderRequest) throws BookWormException {

		validateRequest(getOrderRequest);
		validateOrderId(getOrderRequest.getOrderId());

	}
	
	public void validateGetOrdersRequest(GetOrdersRequest getOrdersRequest) throws BookWormException {
		
		validateRequest(getOrdersRequest);
		
		Date startDate = getOrdersRequest.getStartDate();
		Date endDate = getOrdersRequest.getEndDate();

		if(startDate == null) {
			log.error("Error during validation of order list request request. Details: StartDate cannot be null.");
			throw new BookWormException("StartDate cannot be null.");
		}
		
		if(endDate == null) {
			log.error("Error during validation of order list request request. Details: EndDate cannot be null.");
			throw new BookWormException("EndDate cannot be null.");
		}
		
		if(startDate.after(endDate)) {
			log.error("Error during validation of order list request request. Details: StartDate cannot be after endDate.");
			throw new BookWormException("StartDate cannot be after endDate.");
		}
		
	}
}
