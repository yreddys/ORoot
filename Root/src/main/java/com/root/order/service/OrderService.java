package com.root.order.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.root.admin.entity.Product;
import com.root.admin.repository.ProductRepository;
import com.root.order.entity.OrderAddress;
import com.root.order.entity.OrderItem;
import com.root.order.entity.OrderRequest;
import com.root.order.entity.OrderStatus;
import com.root.order.entity.OrderSummaryResponse;
import com.root.order.entity.Orders;
import com.root.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Place an order and save it to the database.
	 * 
	 * @param orderRequest Request containing order details.
	 * @return Unique order ID.
	 * @throws Exception If product not found or other errors occur.
	 */
	public OrderSummaryResponse placeOrder(OrderRequest orderRequest) throws Exception {
		// Create a new order
		Orders order = new Orders();
		order.setOrderId(generateOrderId()); // Use the custom 8-digit generator
		order.setOrderDate(LocalDate.now());
		order.setStatus(OrderStatus.IN_PROGRESS);

		// Transform items into OrderItems
		List<OrderItem> orderItems = new ArrayList<>();
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<OrderSummaryResponse.ItemSummary> itemSummaries = new ArrayList<>();

		for (OrderRequest.Item item : orderRequest.getItems()) {
			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new Exception("Product not found with ID: " + item.getProductId()));

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(product.getPrice());

			orderItems.add(orderItem);
			totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

			// Prepare item summary
			OrderSummaryResponse.ItemSummary itemSummary = new OrderSummaryResponse.ItemSummary();
			itemSummary.setProductName(product.getName());
			itemSummary.setQuantity(item.getQuantity());
			itemSummary.setPrice(product.getPrice());
			itemSummaries.add(itemSummary);
		}

		order.setOrderItems(orderItems);
		order.setTotalAmount(totalAmount);

		// Transform address into OrderAddress
		OrderAddress address = new OrderAddress();
		address.setFirstName(orderRequest.getAddress().getFirstName());
		address.setLastName(orderRequest.getAddress().getLastName());
		address.setEmail(orderRequest.getAddress().getEmail());
		address.setMobileNo(orderRequest.getAddress().getMobileNo());
		address.setAddress(orderRequest.getAddress().getAddress());
		address.setCity(orderRequest.getAddress().getCity());
		address.setState(orderRequest.getAddress().getState());
		address.setPincode(orderRequest.getAddress().getPincode());

		order.setOrderAddress(address);

		// Save order to the database
		Orders savedOrder = orderRepository.save(order);

		// Prepare the order summary response
		OrderSummaryResponse response = new OrderSummaryResponse();
		response.setOrderId(savedOrder.getOrderId());
		response.setTotalAmount(totalAmount);
		response.setItems(itemSummaries);

		OrderSummaryResponse.AddressSummary addressSummary = new OrderSummaryResponse.AddressSummary();
		addressSummary.setFirstName(address.getFirstName());
		addressSummary.setLastName(address.getLastName());
		addressSummary.setEmail(address.getEmail());
		addressSummary.setMobileNo(address.getMobileNo());
		addressSummary.setAddress(address.getAddress());
		addressSummary.setCity(address.getCity());
		addressSummary.setState(address.getState());
		addressSummary.setPincode(address.getPincode());

		response.setAddress(addressSummary);

		return response;
	}

	/**
	 * Generate a unique 8-digit numeric order ID.
	 * 
	 * @return A randomly generated 8-digit number as a string.
	 */
	private String generateOrderId() {
		Random random = new Random();
		int orderId = 10000000 + random.nextInt(90000000); // Ensure 8 digits
		return String.valueOf(orderId);
	}
}
