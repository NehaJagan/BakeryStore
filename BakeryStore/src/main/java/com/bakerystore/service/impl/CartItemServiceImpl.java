package com.bakerystore.service.impl;

import java.math.BigDecimal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.DelicacyToCartItem;
import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;
import com.bakerystore.repository.CartItemRepository;
import com.bakerystore.repository.DelicacyToCartItemRepository;
import com.bakerystore.service.CartItemService;


@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private DelicacyToCartItemRepository delicacyToCartItemRepository;

	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bd2 = new BigDecimal(cartItem.getQty());
		BigDecimal bd1 = new BigDecimal(cartItem.getDelicacy().getOurPrice());
				bd1=bd1.multiply(bd2);
		
		bd1 = bd1.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bd1);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}
	
	public CartItem addDelicacyToCartItem(Delicacy delicacy, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItemList) {
			if(delicacy.getId() == cartItem.getDelicacy().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(delicacy.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setDelicacy(delicacy);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(delicacy.getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		DelicacyToCartItem delicacyToCartItem = new DelicacyToCartItem();
		delicacyToCartItem.setDelicacy(delicacy);
		delicacyToCartItem.setCartItem(cartItem);
		delicacyToCartItemRepository.save(delicacyToCartItem);
		
		return cartItem;
	}
	
	public CartItem findById(Long id) {
		return cartItemRepository.findById(id).get();
	}
	
	public void removeCartItem(CartItem cartItem) {
		delicacyToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}


}
