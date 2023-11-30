package com.bookworm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.error.BookWormException;
import com.bookworm.mapper.BookMapper;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;
import com.bookworm.validator.BookValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService extends BaseService {

	@Autowired
	private BookValidator bookValidator;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookDao bookDao;
	

	public SaveBookResponse saveBook(SaveBookRequest saveBookRequest) throws BookWormException {

		Book book = null;
		Book savedBook = null;
		SaveBookResponse saveBookResponse = null;
		
		bookValidator.validateSaveBookRequest(saveBookRequest);
		book = bookMapper.toBook(saveBookRequest);
		savedBook = bookDao.save(book);
		saveBookResponse = bookMapper.toSaveBookResponse(savedBook);
		
		log.info("Book saved. SaveBookResponse: " + saveBookResponse.toString() + " User:" + userService.getUser());

		return saveBookResponse;

	}
	
	public GetBookResponse getBook(GetBookRequest getBookRequest) throws BookWormException {
		
		Optional<Book> retrievedBook = null;
		GetBookResponse getBookResponse = null;
		
		bookValidator.validateGetBookRequest(getBookRequest);
		retrievedBook = bookDao.findById(getBookRequest.getBookId());
		getBookResponse = bookMapper.toGetBookResponse(retrievedBook.orElseThrow());
		
		log.info("Book retrieved. SaveBookResponse: " + getBookResponse.toString() + " User:" + userService.getUser());

		return getBookResponse;

	}

	public UpdateBookStockResponse updateBookStock(UpdateBookStockRequest saveBookStockRequest) throws BookWormException {

		Book book = null;
		Book savedBook = null;
		UpdateBookStockResponse saveBookStockResponse = null;

		bookValidator.validateSaveBookStockRequest(saveBookStockRequest);
		book = bookMapper.toBook(saveBookStockRequest);
		savedBook = bookDao.save(book);
		saveBookStockResponse = bookMapper.toSaveBookStockResponse(savedBook);
		
		log.info("Book stock updated. SaveBookStockResponse: " + saveBookStockResponse.toString() + " User:" + userService.getUser());
		
		return saveBookStockResponse;

	}

}
