package com.bookworm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetMonthlyStatisticsRequest;
import com.bookworm.model.GetMonthlyStatisticsResponse;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.StatisticsValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticsService {
	
	@Autowired
	private StatisticsValidator statisticsValidator;
	
	@Autowired
	private OrderRepository orderRepository;
	

	public List<GetMonthlyStatisticsResponse> getMonthlyStatistics(GetMonthlyStatisticsRequest getMonthlyStatisticsRequest) throws BookWormException {
		
		List<GetMonthlyStatisticsResponse> aggregationResult = null;

		statisticsValidator.validateStatisticsRequest(getMonthlyStatisticsRequest);
		aggregationResult = orderRepository.findMonthlyStatistics(getMonthlyStatisticsRequest.getCustomerId());	
		
		log.info("Monthly statistics retrieved for customer:" + getMonthlyStatisticsRequest.getCustomerId());
		
		return aggregationResult;

	}
}
