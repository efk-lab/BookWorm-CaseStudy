package com.bookworm.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class SaveBookRequest implements Serializable {

	private static final long serialVersionUID = 3991036946056972798L;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String bookIsbn;

	@NotNull
	@NotBlank
	@NotEmpty
	private String bookName;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String authorName;
	
	@NotNull
	@NotBlank
	@NotEmpty
	private String publisher;
	
	@NotNull
	private Date publishDate;

	@Min(value = 0)
	private int stockCount;

}
