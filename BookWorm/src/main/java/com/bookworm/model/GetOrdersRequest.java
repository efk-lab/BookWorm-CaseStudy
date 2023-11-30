package com.bookworm.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class GetOrdersRequest implements Serializable {

	private static final long serialVersionUID = 8827420892743086093L;

	private Date startDate;
	
	private Date endDate;

}
