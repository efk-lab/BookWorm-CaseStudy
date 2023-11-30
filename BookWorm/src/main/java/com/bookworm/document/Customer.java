package com.bookworm.document;

import java.io.Serializable;

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

@Document(collection = "customer")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder
public class Customer extends BaseDocument implements Serializable {
	
	private static final long serialVersionUID = 6386561593072747693L;

	@Id
	private ObjectId customerId;
	
	@Field
	private String fullName;
	
	@Field
	private String address;
	
	@Field
	private String telNo;
	
	@DBRef
	private User user;

}
