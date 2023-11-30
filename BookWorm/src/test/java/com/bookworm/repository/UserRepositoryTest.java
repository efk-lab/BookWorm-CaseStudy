package com.bookworm.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookworm.conf.mongodb.MongoDBTestConfiguration;
import com.bookworm.document.User;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	

	@BeforeEach
	public void setUp() {
		userRepository.deleteAll();
	}


	@Test
	public void testSaveUser() throws Exception {

		User user = User.builder().email("xxx@gmail.com").password("1234").build();

		User savedUser = userRepository.save(user);
		User foundUser = userRepository.findById(savedUser.getUserId()).get();
		
		assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());

	}
	

	@Test
	public void findByEmail() {

		User user = User.builder().email("xxx@gmail.com").password("1234").build();
		
		User savedUser = userRepository.save(user);
		User foundUser = userRepository.findByEmail("xxx@gmail.com").get();
		
		assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());

	}
	
}