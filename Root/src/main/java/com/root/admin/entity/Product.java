package com.root.admin.entity;

import java.math.BigDecimal;
import java.sql.Blob;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.root.admin.service.BlobToBase64Serializer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String category;
	private BigDecimal price;
	// private boolean isAvailable = true;
	@Lob
	@JsonSerialize(using = BlobToBase64Serializer.class)
	private Blob photo;

}
