package com.bookworm.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bookworm.controller.UserRegistryController;
import com.bookworm.error.BookWormException;
import com.bookworm.model.SignUpRequest;
import com.bookworm.model.SignUpResponse;
import com.bookworm.service.UserRegistryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserRegistryControllerImpl extends BaseControllerImpl implements UserRegistryController {

	@Autowired
	private UserRegistryService userRegistryService;
	

	@Override
	public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) throws BookWormException {
		
		log.info("SignUpRequest received: " + signUpRequest.toString() + " RequestedBy: " + userService.getUser());

		SignUpResponse signUpResponse = userRegistryService.signUp(signUpRequest);

		if (signUpResponse != null) {
			return new ResponseEntity<>(signUpResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(signUpResponse, HttpStatus.NO_CONTENT);
		}
		
	}

}
