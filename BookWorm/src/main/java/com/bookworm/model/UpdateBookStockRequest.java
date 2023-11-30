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
public class UpdateBookStockRequest implements Serializable{

	private static final long serialVersionUID = -4448907498835256240L;

	private ObjectId bookId;
	
	@Min(value = 0)
	private int bookStock;

}
