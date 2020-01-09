package com.bakerystore.service;

import com.bakerystore.domain.UserShipping;

public interface UserShippingService {

UserShipping findById(Long id);
	
	void removeById(Long id);
}
