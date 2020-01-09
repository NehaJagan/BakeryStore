package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByname(String name);
	
	
}
