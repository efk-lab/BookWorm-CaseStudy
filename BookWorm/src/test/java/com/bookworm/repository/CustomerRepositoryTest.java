package com.bookworm.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookworm.conf.mongodb.MongoDBTestConfiguration;
import com.bookworm.document.Customer;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	

	@BeforeEach
	public void setUp() {
		customerRepository.deleteAll();
	}
	
	@Test
	public void testSaveCustomer() throws Exception {
		Customer customer = Customer.builder().customerId(new ObjectId("62d322ddf9f5e01864bed242")).fullName("xyz").build();

		Customer savedCustomer = customerRepository.save(customer);
		Customer foundCustomer = customerRepository.findById(new ObjectId("62d322ddf9f5e01864bed242")).get();
		
		assertThat(foundCustomer.getFullName()).isEqualTo(savedCustomer.getFullName());
	}
	
}
