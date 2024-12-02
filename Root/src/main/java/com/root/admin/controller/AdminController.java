package com.root.admin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;
import com.root.admin.exception.InternalServerException;
import com.root.admin.exception.ResourceNotFoundException;
import com.root.admin.service.AdminService;

@RestController
@RequestMapping("/admin")

public class AdminController {
	/**
	 * 
	 *  Admin module to add product ,update product ,delete product and get Product
	 *
	 */
	@Autowired
	private AdminService adminService;

	/**
	 * API Endpoint to add a new product.
	 * @author yreddys
	 * @param Product
	 * @return
	 * @throws SerialException
	 * @throws SQLException
	 * @throws IOException
	 */
	@PostMapping("/add-product")
	ResponseEntity<?> createProduct(
			@RequestParam("name") String name,
			@RequestParam("category") String category,
			@RequestParam("Price") BigDecimal Price,
			@RequestParam("photo") MultipartFile photo)
			throws SerialException, SQLException, IOException {
		adminService.createProduct(name, category, Price, photo);
		return ResponseEntity.ok("Product added successfully!");
	}
	
	/**
	 * API Endpoint to update product.
	 * @author yreddys
	 * @param productId
	 * @param Product
	 * @return
	 * @throws IOException
	 * @throws ResourceNotFoundException
	 * @throws SQLException
	 * @throws InternalServerException
	 */
	
	
	 @PutMapping("/update/{productId}")
	ResponseEntity<?> updateProduct(
			@PathVariable Long productId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) BigDecimal Price,
			@RequestParam(required = false) MultipartFile photo) throws IOException, ResourceNotFoundException, SQLException, InternalServerException{
		 byte[] photoBytes = photo != null && !photo.isEmpty() ?
				 photo.getBytes() :adminService.getRoomPhotoByRoomId(productId);
		 Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
		 Product pro= new Product();
		 pro.setPhoto(photoBlob);
		adminService.updateProduct(productId,name, category, Price, photoBytes);
		return ResponseEntity.ok("Product updated successfully!");
	}
}
