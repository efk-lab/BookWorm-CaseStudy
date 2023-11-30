package com.bookworm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bookworm.constant.Role;
import com.bookworm.document.User;
import com.bookworm.model.SignUpRequest;
import com.bookworm.model.SignUpResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserMapper extends BaseMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public User toUser(SignUpRequest signUpRequest) {

		log.info("Mapping SignUpRequest to User. SignUpRequest: " + signUpRequest.getEmail() + " User:" + userService.getUser());

		return User.builder()
				.email(signUpRequest.getEmail())
				.password(passwordEncoder.encode(signUpRequest.getPassword()))
				.role(Role.USER)
				.build();

	}

	public SignUpResponse toSignUpResponse(User user) {

		log.info("Mapping User to toSignUpResponse. User:" + userService.getUser());

		SignUpResponse signUpResponse = SignUpResponse.builder()
														.userId(user.getUserId())
														.email(user.getEmail())
														.build();
		
		return (SignUpResponse)toBaseResponse(signUpResponse, user);

	}
}
