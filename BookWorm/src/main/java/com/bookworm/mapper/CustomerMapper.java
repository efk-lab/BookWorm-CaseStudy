package com.bookworm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.bookworm.document.Customer;
import com.bookworm.document.Order;
import com.bookworm.model.GetCustomerOrdersResponse;
import com.bookworm.model.SaveCustomerRequest;
import com.bookworm.model.SaveCustomerResponse;
import com.bookworm.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerMapper extends BaseMapper {
	
	@Autowired
	private UserRepository userRepository;
	

	public Customer toCustomer(SaveCustomerRequest saveCustomerRequest) {

		log.info("Mapping SaveBookRequest to Book. SaveBookRequest:" + saveCustomerRequest.toString() + " User:" + userService.getUser());

		return Customer.builder()
				.fullName(saveCustomerRequest.getFullName())
				.address(saveCustomerRequest.getAddress())
				.telNo(saveCustomerRequest.getTelNo())
				.user(userRepository.findById(saveCustomerRequest.getUserId()).get())
				.build();

	}

	public SaveCustomerResponse toSaveCustomerResponse(Customer customer) {
		
		log.info("Mapping Customer to Book. SaveCustomerResponse:" + customer.toString() + " User:" + userService.getUser());

		SaveCustomerResponse saveCustomerResponse = SaveCustomerResponse.builder()
				.customerId(customer.getCustomerId())
				.customerFullName(customer.getFullName())
				.userId(customer.getUser().getUserId())
				.build();

		return (SaveCustomerResponse)toBaseResponse(saveCustomerResponse, customer);
	}

	public Page<GetCustomerOrdersResponse> toGetCustomerOrdersResponse(Page<Order> orderPage) {
		
		log.info("Mapping Order to GetCustomerOrdersResponse. OrderPage:" + orderPage.toString() + " User:" + userService.getUser());

		List<GetCustomerOrdersResponse> orderResultDtoList = new ArrayList<GetCustomerOrdersResponse>();
		List<Order> orders = orderPage.getContent();

		orders.stream().forEach(order -> orderResultDtoList.add(GetCustomerOrdersResponse.builder()
				.customerId(order.getCustomer().getCustomerId())
				.bookId(order.getBook().getBookId())
				.orderAmount(order.getOrderAmount())
				.orderCount(order.getBookCount())
				.build()));

        return new PageImpl<>(orderResultDtoList, orderPage.getPageable(), orderPage.getTotalElements());

	}

}
