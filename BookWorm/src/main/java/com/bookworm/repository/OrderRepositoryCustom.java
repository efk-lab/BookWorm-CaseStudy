package com.bookworm.repository;

import java.util.List;

import org.bson.types.ObjectId;

import com.bookworm.model.GetMonthlyStatisticsResponse;

public interface OrderRepositoryCustom {
	
	public List<GetMonthlyStatisticsResponse> findMonthlyStatistics(ObjectId customerId);
	
}
