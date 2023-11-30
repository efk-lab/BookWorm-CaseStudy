package com.bookworm.model;

import java.io.Serializable;
import java.math.BigInteger;

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
public class GetMonthlyStatisticsResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -4878484117261818397L;

	private String month;
	
	private String year;
	
	private int totalOrderCount;
	
	private int totalBookCount;
	
	private BigInteger totalPurchasedAmount;

}
