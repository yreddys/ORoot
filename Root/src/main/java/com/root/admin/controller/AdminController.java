package com.root.admin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.root.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {
	@Autowired
	private AdminService adminService;

	/**
	 * API Endpoint to add a new product to the system.
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
}
