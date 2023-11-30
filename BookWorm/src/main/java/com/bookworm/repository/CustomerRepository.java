package com.bookworm.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bookworm.document.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId>{
	

}
