package com.bakerystore.repository;






import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bakerystore.domain.Delicacy;


public interface DelicacyRepository extends CrudRepository<Delicacy, Long> {

	
	Optional<Delicacy> findById(Long id);

	List<Delicacy> findByCategory(String category);

	List<Delicacy> findByNameContaining(String name);

	
	

}
