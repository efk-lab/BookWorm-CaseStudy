package com.bookworm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookworm.conf.security.UserService;
import com.bookworm.document.User;
import com.bookworm.mapper.UserMapper;
import com.bookworm.model.SignUpRequest;
import com.bookworm.model.SignUpResponse;
import com.bookworm.repository.UserRepository;
import com.bookworm.validator.UserValidator;


@ExtendWith(MockitoExtension.class)
public class UserRegistryServiceTest {
	
	@Mock
	private UserMapper userMapper;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserValidator userValidator;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserRegistryService userRegistryService;
	
	@Test
	public void testSignUp() throws Exception {
		
		SignUpRequest signUpRequest = new SignUpRequest(); 
		SignUpResponse signUpResponseExpected = SignUpResponse.builder().userId(new ObjectId("62d322ddf9f5e01864bed242")).email("xyz@gmail.com").build();
		User user = User.builder().userId(new ObjectId("62d322ddf9f5e01864bed242")).email("xyz@gmail.com").build();

		doNothing().when(userValidator).validateSignUpRequest(signUpRequest);
		given(userMapper.toUser(any(SignUpRequest.class))).willReturn(user);
		given(userRepository.save(any(User.class))).willReturn(user);
		given(userMapper.toSignUpResponse(any(User.class))).willReturn(signUpResponseExpected);

		SignUpResponse signUpResponseActual = userRegistryService.signUp(signUpRequest);

		assertThat(signUpResponseActual.getUserId()).isEqualTo(signUpResponseExpected.getUserId());
		
	}
	
}
