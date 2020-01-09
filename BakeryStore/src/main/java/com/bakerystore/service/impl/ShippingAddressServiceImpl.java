package com.bakerystore.service.impl;

import org.springframework.stereotype.Service;

import com.bakerystore.domain.ShippingAddress;
import com.bakerystore.domain.UserShipping;
import com.bakerystore.service.ShippingAddressService;


@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Override
	public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
		
		shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
		shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
		shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
		shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
		shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
		shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());
		shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
		return shippingAddress;
	}

}
