package com.root.admin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;
import com.root.admin.exception.InternalServerException;
import com.root.admin.exception.ResourceNotFoundException;

public interface AdminService {

	Product createProduct(String name, String category, BigDecimal price, MultipartFile photo) throws SerialException, SQLException, IOException;

	Product updateProduct(Long productId, String name, String category, BigDecimal price, byte[] photoBytes) throws InternalServerException;

	byte[] getRoomPhotoByRoomId(Long productId) throws ResourceNotFoundException, SQLException;

	void deleteProduct(Long productId);

	List<Product> getAllProducts();

}
