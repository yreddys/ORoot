package com.root.admin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;

public interface AdminService {

	Product createProduct(String name, String category, BigDecimal price, MultipartFile photo) throws SerialException, SQLException, IOException;

}
