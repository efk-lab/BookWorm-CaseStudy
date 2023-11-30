package com.bookworm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookMapper extends BaseMapper {
	
	@Autowired
	private BookDao bookDao;
	

	public Book toBook(SaveBookRequest saveBookRequest) {

		log.info("Mapping SaveBookRequest to Book. SaveBookRequest:" + saveBookRequest.toString() + " User:" + userService.getUser());
		
		return Book.builder()
			  .bookIsbn(saveBookRequest.getBookIsbn())
			  .bookName(saveBookRequest.getBookName())
			  .authorName(saveBookRequest.getAuthorName())
			  .publisher(saveBookRequest.getPublisher())
			  .publishDate(saveBookRequest.getPublishDate())
			  .stockCount(saveBookRequest.getStockCount())
			  .build();		   

	}

	public Book toBook(UpdateBookStockRequest saveBookStockRequest) {

		log.info("Mapping SaveBookStockRequest to Book. SaveBookStockRequest:" + saveBookStockRequest.toString() + " User:" + userService.getUser());
		
		Book book = bookDao.findById(saveBookStockRequest.getBookId()).get();
		book.setStockCount(saveBookStockRequest.getBookStock());

		return book;

	}

	public SaveBookResponse toSaveBookResponse(Book book) {

		log.info("Mapping Book to SaveBookResponse. Book:" + book.toString() + " User:" + userService.getUser());

		SaveBookResponse saveBookResponse = SaveBookResponse.builder()
				.bookId(book.getBookId())
				.bookName(book.getBookName())
				.stockCount(book.getStockCount())
				.build();
		
		return (SaveBookResponse)toBaseResponse(saveBookResponse, book);

	}
	
	public GetBookResponse toGetBookResponse(Book book) {

		log.info("Mapping Book to GetBookResponse. Book:" + book.toString() + " User:" + userService.getUser());

		GetBookResponse getBookResponse = GetBookResponse.builder()
				.bookId(book.getBookId())
				.bookIsbn(book.getBookIsbn())
				.bookName(book.getBookName())
				.authorName(book.getAuthorName())
				.publisher(book.getPublisher())
				.publishDate(book.getPublishDate())
				.stockCount(book.getStockCount())
				.build();
		
		return (GetBookResponse)toBaseResponse(getBookResponse, book);

	}

	public UpdateBookStockResponse toSaveBookStockResponse(Book book) {

		log.info("Mapping Book to SaveBookStockResponse. SaveBookStockResponse:" + book.toString() + " User:" + userService.getUser());
		
		UpdateBookStockResponse updateBookStockResponse = UpdateBookStockResponse.builder()
				.bookId(book.getBookId())
				.bookName(book.getBookName())
				.stockCount(book.getStockCount())
				.build();
	   
		return (UpdateBookStockResponse)toBaseResponse(updateBookStockResponse, book);
		
	}

}