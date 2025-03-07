package com.bakerystore.service.impl;

import org.springframework.stereotype.Service;

import com.bakerystore.domain.BillingAddress;
import com.bakerystore.domain.UserBilling;
import com.bakerystore.service.BillingAddressService;

@Service
public class BillingAddressServiceImpl implements BillingAddressService {

	@Override
	public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressState(userBilling.getUserBillingState());
		billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		return billingAddress;
	}

}
