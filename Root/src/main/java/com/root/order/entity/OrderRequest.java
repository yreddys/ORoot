package com.root.order.entity;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

	private List<Item> items; // List of items in the order
	private Address address; // Shipping address details

	@Data
	public static class Item {
		private Long productId; // Product ID for the order item
		private Integer quantity; // Quantity of the product
	}

	@Data
	public static class Address {
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
