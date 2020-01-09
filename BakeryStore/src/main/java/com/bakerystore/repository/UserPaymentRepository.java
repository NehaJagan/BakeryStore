package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {

	
	
}
