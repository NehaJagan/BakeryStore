package com.bakerystore.service;


import java.util.List;

import java.util.Optional;

import com.bakerystore.domain.Delicacy;


public interface DelicacyService {

	
	 List <Delicacy> findAll();
	Delicacy  findById(Long id);
	
List<Delicacy> findByCategory(String category);
	
	List<Delicacy> blurrySearch(String name);
}
