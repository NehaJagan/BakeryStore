package com.bakerystore.service;

import java.util.List;


import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addDelicacyToCartItem(Delicacy book, User user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
}
