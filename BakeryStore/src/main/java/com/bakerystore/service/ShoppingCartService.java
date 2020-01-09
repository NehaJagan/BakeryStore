package com.bakerystore.service;

import com.bakerystore.domain.ShoppingCart;


public interface ShoppingCartService {
	 public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);
	 public void clearShoppingCart(ShoppingCart shoppingCart);
}
