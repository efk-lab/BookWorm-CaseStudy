package com.bookworm.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection="order")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class Order extends BaseDocument implements Serializable {

	private static final long serialVersionUID = -3440499804922791256L;

	@Id
	private ObjectId orderId;
	
	@DBRef
	private Customer customer;
	
	@DBRef
	private Book book;
	
	@Field
	private int bookCount;
	
	@Field
	private double orderAmount;
	
	@Field
	private Date orderDate;

}
