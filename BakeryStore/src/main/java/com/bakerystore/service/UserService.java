package com.bakerystore.service;



import java.util.Set;

import com.bakerystore.domain.User;
import com.bakerystore.domain.UserBilling;
import com.bakerystore.domain.UserPayment;
import com.bakerystore.domain.UserShipping;
import com.bakerystore.domain.security.PasswordResetToken;
import com.bakerystore.domain.security.UserRole;

public interface UserService {

PasswordResetToken getPasswordResetToken(final String token);	
void createPasswordResetTokenForUser(final User user,final String token);	
                  User findByUsername(String username);
                 User findByEmail(String email);
                 User createUser(User user,Set<UserRole> userRoles) throws Exception;
                 User save(User user);
                 
                 void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);
             	
             	void updateUserShipping(UserShipping userShipping, User user);
             	
             	void setUserDefaultPayment(Long userPaymentId, User user);
             	
             	void setUserDefaultShipping(Long userShippingId, User user);
}
