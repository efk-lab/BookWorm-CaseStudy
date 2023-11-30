package com.bookworm.model;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class GetCustomerOrdersResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 7708464035027364332L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId customerId;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId bookId;
	
	private String bookName;
	
	private int orderCount;
	
	private double orderAmount;
	
}
