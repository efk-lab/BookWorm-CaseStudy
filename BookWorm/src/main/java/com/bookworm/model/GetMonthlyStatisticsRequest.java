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
public class GetMonthlyStatisticsRequest implements Serializable {

	private static final long serialVersionUID = -9163873793049046862L;

	private ObjectId customerId;

}
