package com.bookworm.validator;

import org.springframework.stereotype.Component;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetMonthlyStatisticsRequest;

@Component
public class StatisticsValidator extends BaseValidator {

	public void validateStatisticsRequest(GetMonthlyStatisticsRequest getMonthlyStatisticsRequest) throws BookWormException {

	  validateRequest(getMonthlyStatisticsRequest);
	  validateCustomerId(getMonthlyStatisticsRequest.getCustomerId());

	}
}
