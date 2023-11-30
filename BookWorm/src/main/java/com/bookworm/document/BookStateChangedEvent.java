package com.bookworm.document;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.bookworm.constant.BookStateChangedEventType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = "bookStateChangedEvent")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class BookStateChangedEvent implements Serializable {
	
	
	private static final long serialVersionUID = -13252785676818667L;

	@Id
	private ObjectId bookStateChangedEventId;

	@Field
	private String bookId;
	
	@Field
	private String bookName;
	
	@Field
	private int stockCount;

	@Field
	private BookStateChangedEventType bookStateChangedEventType;

	@Field
	private Date eventDate;
	
	@Field
	private String createdBy;
	
	@Field
	private Date creationDate;
	
	
}
