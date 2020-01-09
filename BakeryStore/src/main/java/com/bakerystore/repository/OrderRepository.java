package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.Order;

public interface OrderRepository  extends CrudRepository<Order,Long>{

}
