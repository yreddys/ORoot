package com.root.cart.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.root.admin.entity.Product;
import com.root.admin.exception.ResourceNotFoundException;
import com.root.admin.repository.ProductRepository;
import com.root.cart.entity.Cart;
import com.root.cart.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Add a product to the cart.
	 * 
	 * @param productId
	 * @param quantity
	 * @return Cart
	 * @throws ResourceNotFoundException
	 */
	 public Cart addToCart(Long productId, Integer quantity) throws ResourceNotFoundException {
	        // Fetch the product
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

	        // Check if the product already exists in the cart
	        Cart existingCartItem = cartRepository.findByProductId(productId);

	        if (existingCartItem != null) {
	            // Update the quantity and total price if the product is already in the cart
	            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
	            existingCartItem.setTotalPrice(
	                product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity()))
	            );
	            return cartRepository.save(existingCartItem);
	        }

	        // Create a new cart item if it doesn't exist
	        Cart newCartItem = new Cart();
	        newCartItem.setProduct(product);
	        newCartItem.setQuantity(quantity);
	        newCartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

	        return cartRepository.save(newCartItem);
	    }
	/**
	 * Get all cart items.
	 * 
	 * @return List<Cart>
	 */
	public List<Cart> getCartItems() {
		return cartRepository.findAll();
	}

	/**
	 * Remove an item from the cart.
	 * 
	 * @param cartId
	 */
	public void removeCartItem(Long cartId) {
		cartRepository.deleteById(cartId);
	}

	/**
	 * Clear all items from the cart.
	 */
	public void clearCart() {
		cartRepository.deleteAll();
	}
}
