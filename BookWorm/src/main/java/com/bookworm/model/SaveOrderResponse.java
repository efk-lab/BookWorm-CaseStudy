package com.bookworm.model;

import java.io.Serializable;
import java.util.Date;

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
public class SaveOrderResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -5273089616558170813L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId orderId;
	
	private String customerFullName;
	
	private ObjectId bookId;
	
	private String bookName;
	
	private int orderCount;
	
	private double orderAmount;
	
	private Date orderDate;
	
}
