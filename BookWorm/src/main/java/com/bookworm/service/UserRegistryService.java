package com.bookworm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookworm.document.User;
import com.bookworm.error.BookWormException;
import com.bookworm.mapper.UserMapper;
import com.bookworm.model.SignUpRequest;
import com.bookworm.model.SignUpResponse;
import com.bookworm.repository.UserRepository;
import com.bookworm.validator.UserValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistryService extends BaseService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidator userValidator;
	

	public SignUpResponse signUp(SignUpRequest signUpRequest) throws BookWormException {
		
		SignUpResponse signUpResponse = null;

		userValidator.validateSignUpRequest(signUpRequest);
		User user = userMapper.toUser(signUpRequest);
		User savedUser = userRepository.save(user);
		signUpResponse = userMapper.toSignUpResponse(savedUser);
		
		log.info("User saved. SignUpResponse: " + signUpResponse.toString() + " User:" + userService.getUser());

		return signUpResponse;
		
	}
}
