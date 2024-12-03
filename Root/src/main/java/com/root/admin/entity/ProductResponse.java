package com.root.admin.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
	private Long id;
	private String name;
	private String category;
	private BigDecimal price;
	private String photo;
}
