package com.bakerystore.service;

import com.bakerystore.domain.BillingAddress;
import com.bakerystore.domain.Order;
import com.bakerystore.domain.Payment;
import com.bakerystore.domain.ShippingAddress;

import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;

public interface OrderService {

 Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress, Payment payment, String shippingMethod,User user);
 Order findById(Long id);
	
}
