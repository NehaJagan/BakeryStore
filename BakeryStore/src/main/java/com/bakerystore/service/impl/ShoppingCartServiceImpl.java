package com.bakerystore.service.impl;

import java.math.BigDecimal;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.repository.ShoppingCartRepository;
import com.bakerystore.service.CartItemService;
import com.bakerystore.service.ShoppingCartService;

	@Service
	public class ShoppingCartServiceImpl implements ShoppingCartService{
		
		@Autowired
		private CartItemService cartItemService;
		
		@Autowired
		private ShoppingCartRepository shoppingCartRepository;
		
		public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
			
			BigDecimal cartTotal = new BigDecimal("0.00");
			
			List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
			
			for (CartItem cartItem : cartItemList) {
				if(cartItem.getDelicacy().getInStockNumber() > 0) {
					cartItem=cartItemService.updateCartItem(cartItem);
					cartTotal = cartTotal.add(cartItem.getSubtotal());
				}
				
				
			}
			
			shoppingCart.setGrandTotal(cartTotal);
			
			shoppingCartRepository.save(shoppingCart);
			return shoppingCart;
			
		}

		@Override
		public void clearShoppingCart(ShoppingCart shoppingCart) {
			shoppingCartRepository.delete(shoppingCart);
			
		}

		
		
		
	}

