package com.bookworm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookworm.model.GetMonthlyStatisticsRequest;
import com.bookworm.model.GetMonthlyStatisticsResponse;
import com.bookworm.repository.OrderRepository;
import com.bookworm.validator.StatisticsValidator;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

	@Mock
	private StatisticsValidator statisticsValidator;

	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private StatisticsService statisticsService;
	
	
	@Test
	public void testGetMonthlyStatistics() throws Exception {
		
		GetMonthlyStatisticsRequest getMonthlyStatisticsRequest = new GetMonthlyStatisticsRequest(); 
		getMonthlyStatisticsRequest.setCustomerId(new ObjectId("64f357297b58bd33770d34ec"));
		GetMonthlyStatisticsResponse  getMonthlyStatisticsResponse = GetMonthlyStatisticsResponse.builder().month("October").totalBookCount(100).build();
		List<GetMonthlyStatisticsResponse> monthlyStatisticsResponseExpected = new ArrayList<GetMonthlyStatisticsResponse>();
		monthlyStatisticsResponseExpected.add(getMonthlyStatisticsResponse);
		
		doNothing().when(statisticsValidator).validateStatisticsRequest(getMonthlyStatisticsRequest);
		given(orderRepository.findMonthlyStatistics(any(ObjectId.class))).willReturn(monthlyStatisticsResponseExpected);

		List<GetMonthlyStatisticsResponse> monthlyStatisticsResponseActual = statisticsService.getMonthlyStatistics(getMonthlyStatisticsRequest);

		assertThat(monthlyStatisticsResponseActual.get(0).getTotalBookCount()).isEqualTo(monthlyStatisticsResponseExpected.get(0).getTotalBookCount());
	}

}
