package com.bakerystore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.slf4j.Logger;
//import org.hibernate.annotations.common.util.impl.Log_.logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakerystore.domain.ShoppingCart;
import com.bakerystore.domain.User;
import com.bakerystore.domain.UserBilling;
import com.bakerystore.domain.UserPayment;
import com.bakerystore.domain.UserShipping;
import com.bakerystore.domain.security.PasswordResetToken;
import com.bakerystore.domain.security.UserRole;
import com.bakerystore.repository.PasswordResetTokenRepository;
import com.bakerystore.repository.RoleRepository;
import com.bakerystore.repository.UserPaymentRepository;
import com.bakerystore.repository.UserRepository;
import com.bakerystore.repository.UserShippingRepository;
import com.bakerystore.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	private static final Logger LOG= LoggerFactory.getLogger(UserService.class);

	
	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Autowired
	private UserShippingRepository userShippingRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	
	@Override
	public void createPasswordResetTokenForUser(final User user,final String token) {
		final PasswordResetToken myToken= new PasswordResetToken(token,user);
		 passwordResetTokenRepository.save(myToken);
	}
	
	@Override
	  public User findByUsername(String username) {
		return userRepository.findByUsername(username);
		
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	@Override
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles){
		
		
		User localUser = userRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			
			user.getUserRoles().addAll(userRoles);
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
			
			user.setUserShippingList(new ArrayList<UserShipping>());
			user.setUserPaymentList(new ArrayList<UserPayment>());
			
			
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		save(user);
	}
	
	@Override
	public void updateUserShipping(UserShipping userShipping, User user){
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		save(user);
	}
	
	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();
		
		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}
	
	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();
		
		for (UserShipping userShipping : userShippingList) {
			if(userShipping.getId() == userShippingId) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
	}

	
	
}
