package com.bookworm.validator;

import org.springframework.stereotype.Component;

import com.bookworm.error.BookWormException;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.UpdateBookStockRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookValidator extends BaseValidator {

	public void validateSaveBookRequest(SaveBookRequest saveBookRequest) throws BookWormException {

		validateRequest(saveBookRequest);
		validateBookIsbn(saveBookRequest.getBookIsbn());

		log.info("SaveBookRequest validated. SaveBookRequest: " + saveBookRequest.toString() + " User:" + userService.getUser());

	}

	private void validateBookIsbn(String bookIsbn) {

		if (bookDao.findByBookIsbn(bookIsbn) != null) {
			throw new BookWormException("Book already existed.");
		}

	}

	public void validateSaveBookStockRequest(UpdateBookStockRequest saveBookStockRequest) throws BookWormException {

		validateRequest(saveBookStockRequest);
		validateBookId(saveBookStockRequest.getBookId());

		log.info("SaveBookStockRequest validated. SaveBookStockRequest: " + saveBookStockRequest.toString() + " User:" + userService.getUser());

	}
	
	public void validateGetBookRequest(GetBookRequest getBookRequest) throws BookWormException {

		validateRequest(getBookRequest);
		validateBookId(getBookRequest.getBookId());

		log.info("GetBookRequest validated. GetBookRequest: " + getBookRequest.toString() + " User:" + userService.getUser());

	}

}
