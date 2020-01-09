package com.bakerystore.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakerystore.domain.BillingAddress;
import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.Order;
import com.bakerystore.domain.Payment;
import com.bakerystore.domain.ShippingAddress;

import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;
import com.bakerystore.repository.OrderRepository;
import com.bakerystore.service.CartItemService;
import com.bakerystore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Override
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Delicacy delicacy = cartItem.getDelicacy();
			cartItem.setOrder(order);
			delicacy.setInStockNumber(delicacy.getInStockNumber() - cartItem.getQty());//reduce the quantity of the delicacy after placing a order 
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());//set the grandtotal itself as orderTotal meaning no delivery fee.
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}


	
	

}
