package com.bookworm.repository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookworm.document.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId>, OrderRepositoryCustom {

	@Query("{ 'orderDate' : { $gt: ?0, $lt: ?1 } }")
	public Page<Order> findOrders(Date startDate, Date endDate,  Pageable pageable);

	public Page<Order> findAllByCustomerCustomerId(ObjectId customerId, Pageable pageable);

}
