package com.bookworm.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class GetOrderRequest implements  Serializable {

	private static final long serialVersionUID = 618659638154352626L;
	
	private ObjectId orderId;

}
