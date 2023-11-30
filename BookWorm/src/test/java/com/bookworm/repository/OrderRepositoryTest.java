package com.bookworm.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookworm.conf.mongodb.MongoDBTestConfiguration;
import com.bookworm.document.Customer;
import com.bookworm.document.Order;
import com.bookworm.model.GetMonthlyStatisticsResponse;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;
	

	@BeforeEach
	public void setUp() {
		orderRepository.deleteAll();
	}

	@Test
	public void testSaveOrder() throws Exception {
		Order order = Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).build();

		Order savedOrder = orderRepository.save(order);
		Order foundOrder = orderRepository.findById(new ObjectId("62d322ddf9f5e01864bed242")).get();

		assertThat(foundOrder.getOrderAmount()).isEqualTo(savedOrder.getOrderAmount());
	}

	@Test
	public void testFindOrders() throws Exception {

		Order order = Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).orderDate(new SimpleDateFormat("dd/MM/yyyy").parse("02/11/2023")).build();

		Order savedOrder = orderRepository.save(order);
		Page<Order> foundOrders = orderRepository.findOrders(new SimpleDateFormat("dd/MM/yyyy").parse("01/11/2023"), new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2023"),
				PageRequest.of(0, 1));

		assertThat(foundOrders.getContent().get(0).getOrderAmount()).isEqualTo(savedOrder.getOrderAmount());
	}

	@Test
	public void testFindAllByCustomerCustomerId() throws Exception {
		Customer customer = Customer.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).fullName("xyz").build();
		Order order = Order.builder().orderId(new ObjectId("62d322ddf9f5e01864bed242")).orderAmount(1000).customer(customer).build();

		Order savedOrder = orderRepository.save(order);
		Page<Order> foundOrders = orderRepository.findAllByCustomerCustomerId(new ObjectId("64f357297b58bd33770d34ec"), PageRequest.of(0, 1));

		assertThat(foundOrders.getContent().get(0).getOrderAmount()).isEqualTo(savedOrder.getOrderAmount());
	}
//
//	@Test
//	public void testFindMonthlyStatistics() throws Exception {
//		Customer customer = Customer.builder().customerId(new ObjectId("64f357297b58bd33770d34ec")).fullName("xyz").build();
//		Order order = Order.builder()
//				.orderId(new ObjectId("62d322ddf9f5e01864bed242"))
//				.orderAmount(1000D)
//				.customer(customer).build();
//
//		Order savedOrder = orderRepository.save(order);
//		List<GetMonthlyStatisticsResponse> foundOrders = orderRepository.findMonthlyStatistics(new ObjectId("64f357297b58bd33770d34ec"));
//
//		assertThat(foundOrders.get(0).getTotalBookCount()).isEqualTo(savedOrder.getBookCount());
//	}

}
