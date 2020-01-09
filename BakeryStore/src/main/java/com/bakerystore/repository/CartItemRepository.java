package com.bakerystore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
            
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
