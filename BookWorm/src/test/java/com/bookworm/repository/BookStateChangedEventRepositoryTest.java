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
import com.bookworm.document.BookStateChangedEvent;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(MongoDBTestConfiguration.class)
public class BookStateChangedEventRepositoryTest{
	@Autowired
	private BookStateChangedEventRepository bookStateChangedEventRepository;
	

	@BeforeEach
	public void setUp() {
		bookStateChangedEventRepository.deleteAll();
	}


	@Test
	public void testFindByBookIsbn() throws Exception {

		BookStateChangedEvent bookStateChangedEvent = BookStateChangedEvent.builder().bookStateChangedEventId(new ObjectId("62d322ddf9f5e01864bed242")).bookName("xyz").build();

		BookStateChangedEvent savedStateChangedEvent = bookStateChangedEventRepository.save(bookStateChangedEvent);
		BookStateChangedEvent foundStateChangedEvent = bookStateChangedEventRepository.findById(new ObjectId("62d322ddf9f5e01864bed242")).get();
		
		assertThat(foundStateChangedEvent.getBookName()).isEqualTo(savedStateChangedEvent.getBookName());

	}
}
