package com.bookworm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookworm.conf.security.UserService;
import com.bookworm.dao.BookDao;
import com.bookworm.document.Book;
import com.bookworm.mapper.BookMapper;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;
import com.bookworm.validator.BookValidator;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	private BookValidator bookValidator;

	@Mock
	private BookMapper bookMapper;

	@Mock
	private BookDao bookDao;
	
	@Mock
	private UserService userService;

	@InjectMocks
	private BookService bookService;
	

	@Test
	public void testSaveBook() throws Exception {

		SaveBookRequest saveBookRequest = new SaveBookRequest(); 
		SaveBookResponse saveBookResponseExpected = SaveBookResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		Book book = Book.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		doNothing().when(bookValidator).validateSaveBookRequest(saveBookRequest);
		given(bookMapper.toBook(any(SaveBookRequest.class))).willReturn(book);
		given(bookDao.save(any(Book.class))).willReturn(book);
		given(bookMapper.toSaveBookResponse(any(Book.class))).willReturn(saveBookResponseExpected);

		SaveBookResponse saveBookResponseActual = bookService.saveBook(saveBookRequest);

		assertThat(saveBookResponseActual.getBookName()).isEqualTo(saveBookResponseExpected.getBookName());

	}

	
	@Test
	public void testGetBook() throws Exception {

		GetBookRequest getBookRequest = new GetBookRequest(); 
		getBookRequest.setBookId(new ObjectId("62d322ddf9f5e01864bed242"));
		GetBookResponse getBookResponseExpected = GetBookResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		Optional<Book> book = Optional.of(Book.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build()) ; 

		doNothing().when(bookValidator).validateGetBookRequest(getBookRequest);
		given(bookDao.findById(any(ObjectId.class))).willReturn(book);
		given(bookMapper.toGetBookResponse(any(Book.class))).willReturn(getBookResponseExpected);

		GetBookResponse getBookResponseActual = bookService.getBook(getBookRequest);

		assertThat(getBookResponseActual.getBookName()).isEqualTo(getBookResponseExpected.getBookName());

	}

	
	@Test
	public void testUpdateBookStock() throws Exception {

		UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest(); 
		UpdateBookStockResponse updateBookStockResponseExpected = UpdateBookStockResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();
		Book book = Book.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		doNothing().when(bookValidator).validateSaveBookStockRequest(updateBookStockRequest);
		given(bookMapper.toBook(any(UpdateBookStockRequest.class))).willReturn(book);
		given(bookDao.save(any(Book.class))).willReturn(book);
		given(bookMapper.toSaveBookStockResponse(any(Book.class))).willReturn(updateBookStockResponseExpected);

		UpdateBookStockResponse updateBookStockResponseActual = bookService.updateBookStock(updateBookStockRequest);

		assertThat(updateBookStockResponseActual.getBookName()).isEqualTo(updateBookStockResponseExpected.getBookName());

	}

}
