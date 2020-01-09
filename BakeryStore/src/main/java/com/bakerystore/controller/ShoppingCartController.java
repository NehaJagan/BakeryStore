package com.bakerystore.controller;

import java.security.Principal;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;
import com.bakerystore.service.CartItemService;
import com.bakerystore.service.DelicacyService;
import com.bakerystore.service.ShoppingCartService;
import com.bakerystore.service.UserService;

@Controller
@RequestMapping("/shoppingCart")

public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private DelicacyService delicacyService;
	
	@Autowired
	private CartItemService cartItemService;
	
	
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model,Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart= user.getShoppingCart();
		
		List<CartItem> cartItemList= cartItemService.findByShoppingCart(shoppingCart);
		shoppingCartService.updateShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		return "shoppingCart";
	}
	
	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("delicacy") Delicacy delicacy,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
			) {
		User user = userService.findByUsername(principal.getName());
		  delicacy = delicacyService.findById(delicacy.getId());
		
		if (Integer.parseInt(qty) > delicacy.getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/delicacyDetail?id="+delicacy.getId();
		}
		
		CartItem cartItem = cartItemService.addDelicacyToCartItem(delicacy, user, Integer.parseInt(qty));
		model.addAttribute("addDelicacySuccess", true);
		
		return "forward:/delicacyDetail?id="+delicacy.getId();
	}
	
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);
		
		return "forward:/shoppingCart/cart";
	}
	
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id));
		
		return "forward:/shoppingCart/cart";
	}
	
	
	
}
