package com.bookworm.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookworm.conf.mongodb.MongoDBTestConfiguration;
import com.bookworm.document.Book;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class BookRepositoryTest{

	@Autowired
	private BookRepository bookRepository;
	

	@BeforeEach
	public void setUp() {
		bookRepository.deleteAll();
	}


	@Test
	public void testFindByBookIsbn() throws Exception {

		Book book = Book.builder().bookId(new ObjectId("62d322ddf9f5e01864bed242")).bookIsbn("0-19-853453").build();

		Book savedBook = bookRepository.save(book);
		Book foundBook = bookRepository.findByBookIsbn(savedBook.getBookIsbn());
		
		assertThat(foundBook.getBookIsbn()).isEqualTo(savedBook.getBookIsbn());

	}
	
}
