package com.bakerystore.domain;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.DelicacyToCartItem;
import com.bakerystore.domain.Order;
import com.bakerystore.domain.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int qty;
	private BigDecimal subtotal;
	
	@OneToOne
	private Delicacy delicacy;
	
	@OneToMany(mappedBy = "cartItem")
	@JsonIgnore
	private List<DelicacyToCartItem> delicacyToCartItemList;
	
	@ManyToOne
	@JoinColumn(name="shopping_cart_id")
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	

	

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Delicacy getDelicacy() {
		return delicacy;
	}

	public void setDelicacy(Delicacy delicacy) {
		this.delicacy = delicacy;
	}

	public List<DelicacyToCartItem> getDelicacyToCartItemList() {
		return delicacyToCartItemList;
	}

	public void setDelicacyToCartItemList(List<DelicacyToCartItem> delicacyToCartItemList) {
		this.delicacyToCartItemList = delicacyToCartItemList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
