package com.bakerystore.service.impl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakerystore.domain.Delicacy;
import com.bakerystore.repository.DelicacyRepository;
import com.bakerystore.service.DelicacyService;


@Service
public class DelicacyServiceImpl implements DelicacyService {

	@Autowired
	private DelicacyRepository delicacyRepository;

	@Override
	public List<Delicacy> findAll() {
		
		return (List<Delicacy>) delicacyRepository.findAll();
	}

	@Override
	public Delicacy findById(Long id) {
		 
		return delicacyRepository.findById(id).get();
	}

	
	
	@Override
	public List<Delicacy> findByCategory(String category){
		List<Delicacy> delicacyList = delicacyRepository.findByCategory(category);
		
		List<Delicacy> activeDelicacyList = new ArrayList<>();
		
		for (Delicacy delicacy: delicacyList) {
			if(delicacy.isActive()) {
				activeDelicacyList.add(delicacy);
			}
		}
		
		return activeDelicacyList;
	}
	@Override
	public List<Delicacy> blurrySearch(String name) {
		List<Delicacy> delicacyList = delicacyRepository.findByNameContaining(name);
List<Delicacy> activeDelicacyList = new ArrayList<>();
		
		for (Delicacy delicacy: delicacyList) {
			if(delicacy.isActive()) {
				activeDelicacyList.add(delicacy);
			}
		}
		
		return activeDelicacyList;
	}
	
	

	
	
	
}
