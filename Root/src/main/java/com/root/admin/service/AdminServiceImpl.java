package com.root.admin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;
import com.root.admin.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Product createProduct(String name, String category, BigDecimal price, MultipartFile file)
			throws SerialException, SQLException, IOException {
		Product product = new Product();
		product.setName(name);
		product.setCategory(category);
		product.setPrice(price);
		if (!file.isEmpty()) {
			byte[] photoBytes = file.getBytes();
			Blob photoBlob = new SerialBlob(photoBytes);
			product.setPhoto(photoBlob);
		}
		return adminRepository.save(product);
	}

}
