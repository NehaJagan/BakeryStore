package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;


import com.bakerystore.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
 
}
