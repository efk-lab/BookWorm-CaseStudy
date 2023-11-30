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
public class SaveBookResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = 1627270842835654992L;

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId bookId;
	
	private String bookName;
	
	private int stockCount;

}
