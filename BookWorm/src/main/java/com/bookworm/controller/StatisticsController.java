package com.bookworm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetMonthlyStatisticsRequest;
import com.bookworm.model.GetMonthlyStatisticsResponse;

@RequestMapping("/bookworm/statistics")
public interface StatisticsController {

	@RequestMapping(
			value = "/monthly", 
			method = RequestMethod.POST, 
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<GetMonthlyStatisticsResponse>> getMonthlyStatistics(@Valid @RequestBody GetMonthlyStatisticsRequest statisticsRequestDto) throws BookWormException;
}
