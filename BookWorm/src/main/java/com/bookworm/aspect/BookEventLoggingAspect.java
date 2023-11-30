package com.bookworm.aspect;

import java.util.Date;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookworm.constant.BookStateChangedEventType;
import com.bookworm.constant.Role;
import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.document.BookStateChangedEvent;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.SaveOrderResponse;
import com.bookworm.model.UpdateBookStockResponse;
import com.bookworm.repository.BookStateChangedEventRepository;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class BookEventLoggingAspect {
	
	@Autowired
	private BookStateChangedEventRepository bookStateChangedEventRepository;
	
	@Autowired
	private BookDao bookDao;
	
	

	@AfterReturning(pointcut = "execution(* com.bookworm.service.BookService.saveBook(..))", returning = "returnObject")
	public void logSaveBook(JoinPoint joinPoint, Object returnObject) {

		SaveBookResponse savePackageResponse = (SaveBookResponse) returnObject;
		
	
		BookStateChangedEvent event = BookStateChangedEvent.builder()
															.bookId(savePackageResponse.getBookId().toString())
															.bookName(savePackageResponse.getBookName())
															.stockCount(savePackageResponse.getStockCount())
															.bookStateChangedEventType(BookStateChangedEventType.BOOK_ADDED)
															.eventDate(new Date())
															.createdBy(Role.SYSTEM.name())
															.creationDate(new Date())
															.build();
		
		
		bookStateChangedEventRepository.save(event);
		
		log.info("BOOK_ADDED event occured. Event: " + event.toString());

	}

	@AfterReturning(pointcut = "execution(* com.bookworm.service.BookService.updateBookStock(..))", returning = "returnObject")
	public void logUpdateBookStock(JoinPoint joinPoint, Object returnObject) {

		UpdateBookStockResponse updateBookStockResponse = (UpdateBookStockResponse) returnObject;
		
		
		BookStateChangedEvent event = BookStateChangedEvent.builder()
															.bookId(updateBookStockResponse.getBookId().toString())
															.bookName(updateBookStockResponse.getBookName())
															.stockCount(updateBookStockResponse.getStockCount())
															.bookStateChangedEventType(BookStateChangedEventType.BOOK_STOCK_UPDATED)
															.eventDate(new Date())
															.createdBy(Role.SYSTEM.name())
															.creationDate(new Date())
															.build();
		
		
		bookStateChangedEventRepository.save(event);
		
		log.info("BOOK_STOCK_UPDATED event occured. Event: " + event.toString());

	}

	@AfterReturning(pointcut = "execution(* com.bookworm.service.OrderService.saveOrder(..))", returning = "returnObject")
	public void logSaveOrder(JoinPoint joinPoint, Object returnObject) {

		SaveOrderResponse saveOrderResponse = (SaveOrderResponse) returnObject;
		
		Optional<Book> bookOptional = bookDao.findById(saveOrderResponse.getBookId());
		
		if(bookOptional.isPresent()) {
			Book book = bookOptional.get();
			BookStateChangedEvent event = BookStateChangedEvent.builder()
					.bookId(book.getBookId().toString())
					.bookName(book.getBookName())
					.stockCount(book.getStockCount())
					.bookStateChangedEventType(BookStateChangedEventType.BOOK_STOCK_UPDATED)
					.eventDate(new Date())
					.createdBy(Role.SYSTEM.name())
					.creationDate(new Date())
					.build();


			bookStateChangedEventRepository.save(event);

			log.info("BOOK_STOCK_UPDATED event occured. Event: " + event.toString());
		} else {
			log.info("Cannot log the event. No book found with ID:" + saveOrderResponse.getBookId());
		}
		
	}

}
