package com.bakerystore.service;

import com.bakerystore.domain.UserPayment;

public interface UserPaymentService {
UserPayment findById(Long id);
	
	void removeById(Long id);
}
