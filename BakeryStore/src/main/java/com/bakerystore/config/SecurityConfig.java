package com.bakerystore.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bakerystore.service.impl.UserSecurityService;
import com.bakerystore.utility.SecurityUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)// provide http security,and

public class SecurityConfig  extends  WebSecurityConfigurerAdapter{

	@Autowired
	private Environment env;
	
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = { //these will be available without security configuration
			"/css/**",//whatever path beyond css will be freely accessible
			"/js/**",
			"/image/**",
			"/",
			"/newUser",
			"/forgotPassword",
			
			"/login",
			"/fonts/**",
			"/delicacymenu",
			"/delicacyDetail/**",
			"/shoppingCart/**"
			
			
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	http	
		.authorizeRequests().
		//antMatchers("/**").// in this method any request matching public_matchers will be allowed.
		antMatchers(PUBLIC_MATCHERS).
		permitAll().anyRequest().authenticated();
	
		http
	 		.csrf().disable().cors().disable()
	 		.formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
	 		.loginPage("/login").permitAll()
	 		.and()
	 		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	 		.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
	 		.and()
	 		.rememberMe();
		
	}
	
	@Autowired
	public void  configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//here we encode the password and authenticate the username and password that the user inputs using userSecurityService.
	auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	
		
	}
	
	
}
