package com.bakerystore.service;

import com.bakerystore.domain.ShippingAddress;
import com.bakerystore.domain.UserShipping;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
