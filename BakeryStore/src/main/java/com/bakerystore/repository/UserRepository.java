package com.bakerystore.repository;

import org.springframework.data.repository.CrudRepository;


import com.bakerystore.domain.User;

public interface UserRepository extends CrudRepository<User,Long> {

	User  findByUsername(String username);
	User findByEmail(String email);
	
}


// this crudrepository extends  the paging and the sorting repository for on demand paging. 