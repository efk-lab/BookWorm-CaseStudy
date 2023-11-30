package com.bookworm.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "book")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class Book extends BaseDocument implements Serializable {

	private static final long serialVersionUID = -2342615118789604188L;

	@Id
	private ObjectId bookId;
	
	@Field
	private String bookIsbn;
	
	@Field
	private String bookName;

	@Field
	private String authorName;
	
	@Field
	private String publisher;
	
	@Field
	private Date publishDate;

	@Field
	private int stockCount;
	

}
