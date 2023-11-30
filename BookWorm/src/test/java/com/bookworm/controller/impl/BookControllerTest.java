package com.bookworm.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bookworm.BookWormApplicationTest;
import com.bookworm.conf.security.UserService;
import com.bookworm.model.GetBookRequest;
import com.bookworm.model.GetBookResponse;
import com.bookworm.model.SaveBookRequest;
import com.bookworm.model.SaveBookResponse;
import com.bookworm.model.UpdateBookStockRequest;
import com.bookworm.model.UpdateBookStockResponse;
import com.bookworm.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = {BookWormApplicationTest.class})
@WebMvcTest(BookControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(BookControllerImpl.class)
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;
	
	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean 
	private LocalValidatorFactoryBean validator;
	

	@Test
	public void testSaveBook() throws Exception {
		SaveBookRequest saveBookRequest = new SaveBookRequest();
		SaveBookResponse saveBookResponse = SaveBookResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(bookService.saveBook(any(SaveBookRequest.class))).willReturn(saveBookResponse);

		this.mockMvc.perform(post("/bookworm/book/record")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(saveBookRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("bookName").value("xyz"));
		
	}
	
	
	@Test
	public void testGetBook() throws Exception {
		GetBookRequest getBookRequest = new GetBookRequest();
		GetBookResponse getBookResponse = GetBookResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(bookService.getBook(any(GetBookRequest.class))).willReturn(getBookResponse);

		this.mockMvc.perform(post("/bookworm/book/details")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(getBookRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("bookName").value("xyz"));
		
	}
	
	
	@Test
	public void updateBookStock() throws Exception {
		UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest();
		UpdateBookStockResponse updateBookStockResponse = UpdateBookStockResponse.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		given(userService.getUser()).willReturn("xxx@gmail.com");
		given(bookService.updateBookStock(any(UpdateBookStockRequest.class))).willReturn(updateBookStockResponse);

		this.mockMvc.perform(post("/bookworm/book/stock")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(updateBookStockRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("bookName").value("xyz"));
		
	}

}
