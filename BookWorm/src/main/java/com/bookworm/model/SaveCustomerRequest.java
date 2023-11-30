package com.bookworm.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class SaveCustomerRequest implements Serializable {
	
	private static final long serialVersionUID = 4743679189754386823L;

	@NotNull
	@NotBlank
	@NotEmpty
	private String fullName;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String telNo;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String address;
	
	private ObjectId userId;

}
