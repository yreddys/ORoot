package com.root.admin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;
import com.root.admin.exception.InternalServerException;
import com.root.admin.exception.ResourceNotFoundException;
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

	@Override
	public Product updateProduct(Long productId, String name, String category, BigDecimal price, byte[] photoBytes) throws InternalServerException {
		Product product=adminRepository.findById(productId).get();
		if (name!=null)product.setName(name);
		if (category!=null)product.setCategory(category);
		if (price!=null)product.setPrice(price);
		 if (photoBytes != null && photoBytes.length > 0) {
	            try {
	            	product.setPhoto(new SerialBlob(photoBytes));
	            } catch (SQLException ex) {
	                throw new InternalServerException("Fail updating room");
	            }
	        }
		return adminRepository.save(product);
	}

	
	@Override
	public byte[] getRoomPhotoByRoomId(Long productId) throws ResourceNotFoundException, SQLException {
		 Optional<Product> theProduct = adminRepository.findById(productId);
	        if(theProduct.isEmpty()){
	            throw new ResourceNotFoundException("Sorry, Room not found!");
	        }
	        Blob photoBlob = theProduct.get().getPhoto();
	        if(photoBlob != null){
	            return photoBlob.getBytes(1, (int) photoBlob.length());
	        }
	        return null;
	}

}
