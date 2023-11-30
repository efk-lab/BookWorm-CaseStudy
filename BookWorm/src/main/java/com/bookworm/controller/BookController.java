package com.bookworm.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;

@RequestMapping("/bookworm/book")
@CrossOrigin("*")
@RestController
public interface BookController {
	
	@RequestMapping(
			value = "/record", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SaveBookResponse> saveBook(@Valid @RequestBody SaveBookRequest saveBookRequest) throws BookWormException;
	
	
	@RequestMapping(
			value = "/details", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public ResponseEntity<GetBookResponse> getBook(@Valid @RequestBody GetBookRequest getBookRequest) throws BookWormException;
	
	@RequestMapping(
			value = "/stock", 
			method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UpdateBookStockResponse> updateBookStock(@Valid @RequestBody UpdateBookStockRequest updateBookStockRequest) throws BookWormException;

}
