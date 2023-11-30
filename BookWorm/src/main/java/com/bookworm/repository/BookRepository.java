package com.bookworm.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bookworm.document.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId>{

	Book findByBookIsbn(String bookIsbn);
	
}
