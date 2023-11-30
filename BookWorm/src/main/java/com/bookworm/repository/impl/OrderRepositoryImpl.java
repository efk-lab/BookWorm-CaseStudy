package com.bookworm.repository.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.bookworm.document.Order;
import com.bookworm.model.GetMonthlyStatisticsResponse;
import com.bookworm.repository.OrderRepositoryCustom;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public OrderRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<GetMonthlyStatisticsResponse> findMonthlyStatistics(ObjectId customerId) {

		Aggregation agg = Aggregation.newAggregation(
				Aggregation.match(Criteria.where("customer.customerId").is(customerId)),
				Aggregation.project().andExpression("year(orderDate)").as("year")
									.andExpression("month(orderDate)").as("month")
									.andExpression("toDouble(orderAmount)").as("orderAmount")
									.andInclude("bookCount"),
				Aggregation.group(Aggregation.fields().and("year").and("month"))
									.sum("bookCount").as("totalBookCount")
									.sum("orderAmount").as("totalPurchasedAmount")
									.count().as("totalOrderCount"),
				Aggregation.project().andInclude("year", "month", "totalBookCount", "totalPurchasedAmount", "totalOrderCount"),
				Aggregation.sort(Sort.by(Direction.DESC, "year", "month")));
		

		AggregationResults<GetMonthlyStatisticsResponse> result = mongoTemplate.aggregate(agg, Order.class, GetMonthlyStatisticsResponse.class);
		List<GetMonthlyStatisticsResponse> resultList = result.getMappedResults();

		return resultList;
	}

}
