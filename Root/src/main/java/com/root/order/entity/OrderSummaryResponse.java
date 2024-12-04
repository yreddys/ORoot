package com.root.order.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderSummaryResponse {
	private String orderId; // Confirmation ID
	private BigDecimal totalAmount; // Total cost of the order
	private List<ItemSummary> items; // Summary of ordered items
	private AddressSummary address; // Shipping address summary

	@Data
	public static class ItemSummary {
		private String productName; // Product name
		private Integer quantity; // Quantity ordered
		private BigDecimal price; // Price of each product
	}

	@Data
	public static class AddressSummary {
		private String firstName;
		private String lastName;
		private String email;
		private String mobileNo;
		private String address;
		private String city;
		private String state;
		private String pincode;
	}
}
