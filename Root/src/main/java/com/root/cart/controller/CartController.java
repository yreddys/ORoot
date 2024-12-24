package com.root.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.root.admin.exception.ResourceNotFoundException;
import com.root.cart.entity.Cart;
import com.root.cart.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Add a product to the cart.
     * 
     * @param productId
     * @param quantity
     * @return ResponseEntity<Cart>
     * @throws ResourceNotFoundException
     * @throws MissingServletRequestParameterException 
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long productId, @RequestParam Integer quantity)
            throws ResourceNotFoundException, MissingServletRequestParameterException {
        System.out.println("Received productId: " + productId + ", quantity: " + quantity);
        if (productId == null || quantity == null) {
            throw new MissingServletRequestParameterException("productId or quantity", "Long/Integer");
        }
        Cart cart = cartService.addToCart(productId, quantity);
        return ResponseEntity.ok(cart);
    }



    /**
     * Get all cart items.
     * 
     * @return ResponseEntity<List<Cart>>
     */
    @GetMapping("/items")
    public ResponseEntity<List<Cart>> getCartItems() {
        List<Cart> cartItems = cartService.getCartItems();
        return ResponseEntity.ok(cartItems);
    }

    /**
     * Remove a specific cart item.
     * 
     * @param cartId
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeCartItem(@RequestParam Long cartId) {
        cartService.removeCartItem(cartId);
        return ResponseEntity.ok("Cart item removed successfully!");
    }

    /**
     * Clear all cart items.
     * 
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("All cart items cleared!");
    }
}
