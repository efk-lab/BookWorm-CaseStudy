package com.bookworm.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bookworm.error.BookWormException;
import com.bookworm.model.SignUpRequest;
import com.bookworm.model.SignUpResponse;

@RequestMapping("/bookworm/registry")
public interface UserRegistryController {
	
	@RequestMapping(
			value = "/sign-up", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws BookWormException;

}
