package com.reservation.foodTable.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	private CustomAuthSuccessHandler customAuthSuccessHandler;

		
	   @Bean
	   public UserDetailsService memberDetailsService() {
	      return new FoodMemberDetailsService();
	   }

	   @Bean
	   public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	   }

	   public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider ProviderauthProvider = new DaoAuthenticationProvider();
	      ProviderauthProvider.setUserDetailsService(memberDetailsService());
	      ProviderauthProvider.setPasswordEncoder(passwordEncoder());
	      return ProviderauthProvider;
	   }

	   @Override
	   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	      auth.authenticationProvider(authenticationProvider());
	   }
	   
	   @Bean
	   @Override
	   public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	   

	  
	   	@Override
	   	protected void configure(HttpSecurity http) throws Exception {
	   		http.authorizeRequests()
	   			.antMatchers("/customer-service/oneToOne").hasAnyRole("USER")
	   			.antMatchers("/mypage/**").hasAnyRole("USER")
	   			.antMatchers("/manager/**").hasAnyRole("ADMIN")
	   			.antMatchers("/restaurant-page/**").hasAnyRole("OWNER")
	   			.antMatchers("/restaurant/reservation/**").hasAnyRole("USER")
		   		.anyRequest().permitAll()
		   		.and()
		   		.formLogin()
		   			.loginPage("/login")
		   			.usernameParameter("userId")
		   			.permitAll()
		   			.successHandler(customAuthSuccessHandler)
		   		.and()
		   		.logout()
		   			.logoutSuccessUrl("/")
	       			.permitAll();
	   		
	   }

	   @Override
	   public void configure(WebSecurity web) throws Exception {
	      web.ignoring().antMatchers("/images/**", "/fontawesome/**", "/webjars/**","/webfonts/**");
	   }
}
