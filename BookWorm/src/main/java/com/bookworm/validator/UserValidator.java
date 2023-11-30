package com.bookworm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookworm.error.BookWormException;
import com.bookworm.model.SignUpRequest;
import com.bookworm.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserValidator extends BaseValidator {

	@Autowired
	private UserRepository userRepository;
	

	public void validateSignUpRequest(SignUpRequest signUpRequest) throws BookWormException {

		validateRequest(signUpRequest);
		validateEmail(signUpRequest.getEmail());

		log.info("SignUpRequest validated. SignUpRequest: " + signUpRequest.toString() + " User:" + userService.getUser());

	}

	private void validateEmail(String email) {

		if (userRepository.findByEmail(email).isPresent()) {
			throw new BookWormException("User already registed.");
		}

	}

}
