package com.bakerystore.service;

import com.bakerystore.domain.BillingAddress;
import com.bakerystore.domain.UserBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
	
}
