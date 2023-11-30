package com.bookworm.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bookworm.document.BookStateChangedEvent;

@Repository
public interface BookStateChangedEventRepository extends MongoRepository<BookStateChangedEvent, ObjectId>{

}
