package com.bookworm.dao;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bookworm.document.Book;
import com.bookworm.repository.BookRepository;

@Component
public class BookDao {

	@Autowired
	private BookRepository bookRepository;
	
	
	@CacheEvict(value = "books", allEntries = true) 
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Cacheable(cacheNames = "books", unless="#result == null")
	public Book findByBookIsbn(String bookIsbn) {
		return bookRepository.findByBookIsbn(bookIsbn);
	}
	
	@Cacheable(cacheNames = "books", unless="#result == null")
	public Optional<Book> findById(ObjectId bookId){
		return bookRepository.findById(bookId);
	}	
	
}
