package com.bakerystore.service;

import com.bakerystore.domain.Payment;
import com.bakerystore.domain.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
