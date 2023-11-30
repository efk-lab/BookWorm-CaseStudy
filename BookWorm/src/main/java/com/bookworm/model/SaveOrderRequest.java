package com.bookworm.model;

import java.io.Serializable;

import javax.validation.constraints.Min;

import org.bson.types.ObjectId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class SaveOrderRequest implements Serializable {
	
	private static final long serialVersionUID = 3370790271895841324L;

	private ObjectId customerId;
	
	private ObjectId bookId;
	
	@Min(value = 0)
	private int orderCount;
	
	@Min(value = 0)
	private double orderAmount;

}
