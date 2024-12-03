package com.root.admin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.root.admin.entity.Product;
import com.root.admin.entity.ProductResponse;
import com.root.admin.exception.InternalServerException;
import com.root.admin.exception.ResourceNotFoundException;
import com.root.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
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
	public ResponseEntity<?> createProduct(
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
	public ResponseEntity<?> updateProduct(
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
	 
	 /**  API Endpoint to delete the product.
	  * @author yreddys
	  * @param productId
	  * @return
	  */
		@DeleteMapping("/delete/product/{productId}")
		public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
			adminService.deleteProduct(productId);
			return ResponseEntity.ok("Product deleted successfully!");
		}
/**
 *  API Endpoint to get All Products.
 * @author yreddys
 * @return
 * @throws ResourceNotFoundException
 * @throws SQLException
 */

@GetMapping("/all-products")
public ResponseEntity<List<ProductResponse>> getAllProducts() {
	try {
		// Fetch all products from the service
		List<Product> products = adminService.getAllProducts();

		// Prepare the list of product responses
		List<ProductResponse> productResponses = new ArrayList<>();

		for (Product product : products) {
			// Validate critical fields
			if (product.getId() == null || product.getName() == null) {
				System.err.println("Skipping invalid product: " + product);
				continue; // Skip products with missing ID or name
			}

			// Initialize base64 photo representation
			String base64Photo = null;

			// Handle photo encoding
			if (product.getPhoto() != null) {
				try {
					byte[] photoBytes = product.getPhoto().getBytes(1, (int) product.getPhoto().length());
					if (photoBytes != null && photoBytes.length > 0) {
						base64Photo = Base64.encodeBase64String(photoBytes);
					}
				} catch (SQLException ex) {
					System.err.println("Error processing photo for product ID: " + product.getId());
				}
			}

			// Map product entity to response DTO
			ProductResponse productResponse = new ProductResponse();
			productResponse.setId(product.getId());
			productResponse.setName(product.getName());
			productResponse.setCategory(product.getCategory());
			productResponse.setPrice(product.getPrice());
			productResponse.setPhoto(base64Photo);

			// Add to the response list
			productResponses.add(productResponse);
		}

		// Return the response with OK status
		return ResponseEntity.ok(productResponses);
	} catch (Exception ex) {
		// Log the error and return an internal server error
		System.err.println("Error retrieving products: " + ex.getMessage());
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}

}

