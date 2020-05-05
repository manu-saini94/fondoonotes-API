package com.bridgelabz.fundoonotes.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bridgelabz.fundoonotes.filter.JwtFilter;

@EnableWebSecurity
@Configuration
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtFilter jwtFilter;
	
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests().antMatchers("/**")
		.permitAll()
		.anyRequest().authenticated()
		.and().addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	
	}
}
