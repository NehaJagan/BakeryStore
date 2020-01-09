package com.bakerystore.controller;

import java.security.Principal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bakerystore.domain.Delicacy;
import com.bakerystore.domain.User;
import com.bakerystore.domain.UserBilling;
import com.bakerystore.domain.UserPayment;
import com.bakerystore.domain.UserShipping;
import com.bakerystore.domain.security.PasswordResetToken;
import com.bakerystore.domain.security.Role;
import com.bakerystore.domain.security.UserRole;
import com.bakerystore.service.DelicacyService;
import com.bakerystore.service.UserPaymentService;
import com.bakerystore.service.UserService;
import com.bakerystore.service.UserShippingService;
import com.bakerystore.service.impl.UserSecurityService;
import com.bakerystore.utility.MailConstructor;
import com.bakerystore.utility.SecurityUtility;
import com.bakerystore.utility.USConstants;

@Controller
public class HomeController {
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private UserShippingService userShippingService;
	
	
	@Autowired
	private DelicacyService delicacyService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;//defined in utility
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserSecurityService userSecurityService;

	@RequestMapping("/")
	public String index() {
		
		return"index";
	}
	@RequestMapping("/myAccount")
	public String myAccount() {
		
		return"myAccount";
	}
	
	@RequestMapping("/login")
public String login(Model model) {
	model.addAttribute("classActiveLogin",true);
		
		return "myAccount";
	}
	
	@RequestMapping("/forgotPassword")
	public String forgotPassword( HttpServletRequest request,
			@ModelAttribute("email") String email,
			Model model) {
		

				model.addAttribute("classActiveForgotPassword",true);
				User user= userService.findByEmail(email);
				
				if(user==null) {
					model.addAttribute("emailNotExists", true);
					return "myAccount";
				}
				
				String password = SecurityUtility.randomPassword();//while registering, this randomly sets a password.
				// the user has to go their mail and click on the link to reset their password.
				String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
				
				user.setPassword(encryptedPassword);//  encrypt the randomnly generated password
				
				
				userService.save(user);
				
				
				String token = UUID.randomUUID().toString();//an efficient way to write unique sequence of bytes
				userService.createPasswordResetTokenForUser(user, token); 
				
				String appUrl ="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail( appUrl,request.getLocale(),token,user,password);
				//SimpleMailMessage is a class and mailConstructor is a utility setting the text ,from, to, cc of an email
				mailSender.send(newEmail);//used to send the email
				//the mail is written in mailConstructor utility 
				model.addAttribute("forgotPasswordEmailSent", "true"); //acknowledgement that mail is sent.
				
			
			return "myAccount";
		}
	
		@RequestMapping(value="/newUser",method=RequestMethod.POST)
		public String newUserPost(HttpServletRequest request,@ModelAttribute("email") String userEmail,
									@ModelAttribute("username") String username,
									Model model
									) throws Exception{
			
			model.addAttribute("classActiveNewAccount", true);
			model.addAttribute("email", userEmail);
			model.addAttribute("username", username);
			
			if(userService.findByUsername(username)!=null) {
				model.addAttribute("usernameExists", true);
				return "myAccount";
			}
			if(userService.findByEmail(userEmail)!=null) {
				model.addAttribute("emailExists", true);
				return "myAccount";
			}
			//if username and email does not exists
			
			User user=new User();
			user.setUsername(username);
			user.setEmail(userEmail);
			
			String password = SecurityUtility.randomPassword();//while registering, this randomly sets a password.
			// the user has to go their mail and click on the link to reset their password.
			String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
			
			user.setPassword(encryptedPassword);//  encrypt the randomnly generated password
			
			Role role= new Role();
			role.setRoleId(1);
			role.setName("ROLE_USER");
			Set<UserRole> userRoles= new HashSet<>();
			userRoles.add(new UserRole(user,role));
			userService.createUser(user,userRoles);
			
			
			String token = UUID.randomUUID().toString();//an efficient way to write unique sequence of bytes
			userService.createPasswordResetTokenForUser(user, token); 
			
			String appUrl ="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			
			SimpleMailMessage email = mailConstructor.constructResetTokenEmail( appUrl,request.getLocale(),token,user,password);
			//SimpleMailMessage is a class and mailConstructor is a utility setting the text ,from, to, cc of an email
			mailSender.send(email);//used to send the email
			//the mail is written in mailConstructor utility 
			model.addAttribute("emailSent", "true"); //acknowledgement that mail is sent.
			return "myAccount";
		}
	
	
	
	
	@RequestMapping("/newUser")
	public String newUser(Locale locale,@RequestParam("token") String token,Model model) {
		
		PasswordResetToken passToken= userService.getPasswordResetToken(token);
		if(passToken==null) {//check whether there is a token
			String message= "Invalid Token";
			model.addAttribute("message", message) ;//display error message
			return "redirect:/badRequest";
		}
		
		User user = passToken.getUser();//if there already exists a token ,
		String username= user.getUsername();//assign  username
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);//we get the user details through userSecurityservices
		
		//define an authentication environment and set authentication to the current user
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 
		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit",true);
			
			return "myProfile";
		}
	
	@RequestMapping("/delicacymenu")
	public String delicacymenu(Model model) {
		
		List <Delicacy> delicacyList= delicacyService.findAll();
		model.addAttribute("delicacyList", delicacyList);
		if(delicacyList!=null) {
			model.addAttribute("emptyList", "false");
		}
		else {model.addAttribute("emptyList", "true");}
		return "delicacymenu";
		
		
		
	}
	
	
	@RequestMapping("/delicacyDetail")
	public String delicacyDetail(
			@PathParam("id") Long id, Model model, Principal principal
			) {
		if(principal != null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		Delicacy delicacy = delicacyService.findById(id);
		
		model.addAttribute("delicacy", delicacy);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1); //this is default
		
		return "delicacyDetail";
	}

	@RequestMapping("/myProfile")
	public String myProfile(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		/*model.addAttribute("orderList", user.getOrderList());*/
		
		UserShipping userShipping = new UserShipping();
		model.addAttribute("userShipping", userShipping);
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		List<String> stateList = USConstants.listOfINDIAStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfCreditCards")
	public String listOfCreditCards(
			Model model, Principal principal, HttpServletRequest request
			) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		/*
		 * model.addAttribute("orderList", user.orderList());
		 */
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/listOfShippingAddresses")
	public String listOfShippingAddresses(
			Model model, Principal principal, HttpServletRequest request
			) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		/*model.addAttribute("orderList", user.orderList());*/
		
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping("/addNewCreditCard")
	public String addNewCreditCard(
			Model model, Principal principal
			){
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("addNewCreditCard", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		UserBilling userBilling = new UserBilling();
		UserPayment userPayment = new UserPayment();
		
		
		model.addAttribute("userBilling", userBilling);
		model.addAttribute("userPayment", userPayment);
		
		List<String> stateList = USConstants.listOfINDIAStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		/*model.addAttribute("orderList", user.orderList());*/
		
		return "myProfile";
	}
	
	@RequestMapping("/addNewShippingAddress")
	public String addNewShippingAddress(
			Model model, Principal principal
			){
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		
		model.addAttribute("addNewShippingAddress", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		
		UserShipping userShipping = new UserShipping();
		
		model.addAttribute("userShipping", userShipping);
		
		List<String> stateList = USConstants.listOfINDIAStatesCode;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		/*model.addAttribute("orderList", user.orderList());*/
		
		return "myProfile";
	}
	
	@RequestMapping(value="/addNewCreditCard", method=RequestMethod.POST)
	public String addNewCreditCard(
			@ModelAttribute("userPayment") UserPayment userPayment,
			@ModelAttribute("userBilling") UserBilling userBilling,
			Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		userService.updateUserBilling(userBilling, userPayment, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		return "myProfile";
	}
	
	@RequestMapping(value="/addNewShippingAddress", method=RequestMethod.POST)
	public String addNewShippingAddressPost(
			@ModelAttribute("userShipping") UserShipping userShipping,
			Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		userService.updateUserShipping(userShipping, user);
		
		model.addAttribute("user", user);
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		model.addAttribute("listOfShippingAddresses", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfCreditCards", true);
		
		return "myProfile";
	}
	
	
	@RequestMapping("/updateCreditCard")
	public String updateCreditCard(
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
			) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);
		
		if(user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			UserBilling userBilling = userPayment.getUserBilling();
			model.addAttribute("userPayment", userPayment);
			model.addAttribute("userBilling", userBilling);
			
			List<String> stateList = USConstants.listOfINDIAStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewCreditCard", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping("/updateUserShipping")
	public String updateUserShipping(
			@ModelAttribute("id") Long shippingAddressId, Principal principal, Model model
			) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(shippingAddressId);
		
		if(user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			
			model.addAttribute("userShipping", userShipping);
			
			List<String> stateList = USConstants.listOfINDIAStatesCode;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			model.addAttribute("addNewShippingAddress", true);
			model.addAttribute("classActiveShipping", true);
			model.addAttribute("listOfCreditCards", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping(value="/setDefaultPayment", method=RequestMethod.POST)
	public String setDefaultPayment(
			@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId, Principal principal, Model model
			) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultPayment(defaultPaymentId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveBilling", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		
		return "myProfile";
	}
	
	@RequestMapping(value="/setDefaultShippingAddress", method=RequestMethod.POST)
	public String setDefaultShippingAddress(
			@ModelAttribute("defaultShippingAddressId") Long defaultShippingId, Principal principal, Model model
			) {
		User user = userService.findByUsername(principal.getName());
		userService.setUserDefaultShipping(defaultShippingId, user);
		
		model.addAttribute("user", user);
		model.addAttribute("listOfCreditCards", true);
		model.addAttribute("classActiveShipping", true);
		model.addAttribute("listOfShippingAddresses", true);
		
		model.addAttribute("userPaymentList", user.getUserPaymentList());
		model.addAttribute("userShippingList", user.getUserShippingList());
		
		return "myProfile";
	}
	
	@RequestMapping("/removeCreditCard")
	public String removeCreditCard(
			@ModelAttribute("id") Long creditCardId, Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment = userPaymentService.findById(creditCardId);
		
		if(user.getId() != userPayment.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			userPaymentService.removeById(creditCardId);
			
			model.addAttribute("listOfCreditCards", true);
			model.addAttribute("classActiveBilling", true);
			model.addAttribute("listOfShippingAddresses", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			
			return "myProfile";
		}
	}
	
	@RequestMapping("/removeUserShipping")
	public String removeUserShipping(
			@ModelAttribute("id") Long userShippingId, Principal principal, Model model
			){
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);
		
		if(user.getId() != userShipping.getUser().getId()) {
			return "badRequestPage";
		} else {
			model.addAttribute("user", user);
			
			userShippingService.removeById(userShippingId);
			
			model.addAttribute("listOfShippingAddresses", true);
			model.addAttribute("classActiveShipping", true);
			
			model.addAttribute("userPaymentList", user.getUserPaymentList());
			model.addAttribute("userShippingList", user.getUserShippingList());
			
			return "myProfile";
		}
	}
	
	
	
	
	
	
	
}
