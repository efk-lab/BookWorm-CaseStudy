package com.bookworm.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bookworm.controller.StatisticsController;
import com.bookworm.error.BookWormException;
import com.bookworm.model.GetMonthlyStatisticsRequest;
import com.bookworm.model.GetMonthlyStatisticsResponse;
import com.bookworm.service.StatisticsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class StatisticsControllerImpl extends BaseControllerImpl implements StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	

	public ResponseEntity<List<GetMonthlyStatisticsResponse>> getMonthlyStatistics(GetMonthlyStatisticsRequest getMonthlyStatisticsRequest) throws BookWormException {
		
		log.info("GetOrderRequest received: " + getMonthlyStatisticsRequest.toString() + " RequestedBy: " + userService.getUser());
		
		List<GetMonthlyStatisticsResponse> statisticsResult = statisticsService.getMonthlyStatistics(getMonthlyStatisticsRequest);
		
		if (statisticsResult.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(statisticsResult, HttpStatus.OK);
		}
		
	}

}
