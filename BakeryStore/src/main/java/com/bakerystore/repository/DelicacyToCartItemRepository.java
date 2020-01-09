package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.CartItem;
import com.bakerystore.domain.DelicacyToCartItem;


public interface DelicacyToCartItemRepository extends CrudRepository<DelicacyToCartItem, Long>  {

 void deleteByCartItem(CartItem cartItem);

}
