package com.bookworm.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bookworm.BookWormApplicationTest;
import com.bookworm.conf.security.UserService;
import com.bookworm.model.GetMonthlyStatisticsRequest;
import com.bookworm.model.GetMonthlyStatisticsResponse;
import com.bookworm.service.StatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = {BookWormApplicationTest.class})
@WebMvcTest(StatisticsControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(StatisticsControllerImpl.class)
public class StatisticsControllerTest{
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatisticsService statisticsService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;

	
	@Test
	public void testGetMonthlyStatistics() throws Exception {
		GetMonthlyStatisticsRequest getMonthlyStatisticsRequest = new GetMonthlyStatisticsRequest();
		GetMonthlyStatisticsResponse monthlyStatisticsResponse = GetMonthlyStatisticsResponse.builder().month("October").year("2023").build();
		List<GetMonthlyStatisticsResponse> monthlyStatistics = new ArrayList<GetMonthlyStatisticsResponse>();
		monthlyStatistics.add(monthlyStatisticsResponse);

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(statisticsService.getMonthlyStatistics(any(GetMonthlyStatisticsRequest.class))).willReturn(monthlyStatistics);

		this.mockMvc.perform(post("/bookworm/statistics/monthly")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getMonthlyStatisticsRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].month").value("October"));
	}

}
