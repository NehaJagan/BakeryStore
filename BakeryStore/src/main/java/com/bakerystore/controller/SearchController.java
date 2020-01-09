package com.bakerystore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.User;
import com.bakerystore.service.DelicacyService;
import com.bakerystore.service.UserService;

@Controller
public class SearchController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private DelicacyService delicacyService;

	@RequestMapping("/searchByCategory")
	public String searchByCategory(
			@RequestParam("category") String category,
			Model model, Principal principal
			){
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		String classActiveCategory = "active"+category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		model.addAttribute(classActiveCategory, true);
		
		List<Delicacy> delicacyList = delicacyService.findByCategory(category);
		
		if (delicacyList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "delicacymenu";
		}
		
		model.addAttribute("delicacyList", delicacyList);
		
		return "delicacymenu";
	}
	
	@RequestMapping("/searchDelicacy")
	public String searchDelicacy(
			@ModelAttribute("keyword") String keyword,
			Principal principal, Model model
			) {
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		List<Delicacy> delicacyList = delicacyService.blurrySearch(keyword);
		
		if (delicacyList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "delicacymenu";
		}
		
		model.addAttribute("delicacyList", delicacyList);
		
		return "delicacymenu";
	}
}
