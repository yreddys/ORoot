package com.root.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.root.order.entity.OrderRequest;
import com.root.order.entity.OrderSummaryResponse;
import com.root.order.service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * API to place an order.
	 * 
	 * @param orderRequest Request containing order details.
	 * @return Response with order confirmation ID.
	 */
	@PostMapping("/place-order")
	public ResponseEntity<OrderSummaryResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
		try {
			OrderSummaryResponse response = orderService.placeOrder(orderRequest);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

}
