package com.bookworm.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bookworm.controller.BookController;
import com.bookworm.error.BookWormException;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;
import com.bookworm.service.BookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BookControllerImpl extends BaseControllerImpl implements BookController {

	@Autowired
	private BookService bookService;
	

	@Override
	public ResponseEntity<SaveBookResponse> saveBook(SaveBookRequest saveBookRequest) throws BookWormException {
		
		log.info("SaveBookRequest received: " + saveBookRequest.toString() + " RequestedBy: " + userService.getUser());

		SaveBookResponse saveBookResponse = bookService.saveBook(saveBookRequest);

		if (saveBookResponse != null) {
			return new ResponseEntity<>(saveBookResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveBookResponse, HttpStatus.NO_CONTENT);
		}
		
	}
	
	@Override
	public ResponseEntity<GetBookResponse> getBook(GetBookRequest getBookRequest) throws BookWormException {
		
		log.info("GetBookRequest received: " + getBookRequest.toString() + " RequestedBy: " + userService.getUser());

		GetBookResponse getBookResponse = bookService.getBook(getBookRequest);

		if (getBookResponse != null) {
			return new ResponseEntity<>(getBookResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(getBookResponse, HttpStatus.NO_CONTENT);
		}
		
	}

	@Override
	public ResponseEntity<UpdateBookStockResponse> updateBookStock(UpdateBookStockRequest updateBookStockRequest) throws BookWormException {
		
		log.info("SaveBookStockRequest received: " + updateBookStockRequest.toString() + " RequestedBy: " + userService.getUser());
		
		UpdateBookStockResponse saveBookStockResponse = bookService.updateBookStock(updateBookStockRequest);

		if (saveBookStockResponse != null) {
			return new ResponseEntity<>(saveBookStockResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(saveBookStockResponse, HttpStatus.NO_CONTENT);
		}
		
	}

}
