package com.bakerystore;

import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bakerystore.domain.User;
import com.bakerystore.domain.security.Role;
import com.bakerystore.domain.security.UserRole;
import com.bakerystore.service.UserService;
import com.bakerystore.utility.SecurityUtility;



@SpringBootApplication

public class BakeryStoreApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BakeryStoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("Neha");
		user1.setLastName("Jagan");
		user1.setUsername("j");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("j"));
		user1.setEmail("Njagan@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(1);
		
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		userService.createUser(user1, userRoles);
		
	}
}
