package com.bridgelabz.fundoonotes.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bridgelabz.fundoonotes.serviceimpl.UserServiceImpl;
import com.bridgelabz.fundoonotes.utility.Utility;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	Utility utility;
	
	@Autowired
	UserServiceImpl user;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	String header=request.getHeader("header");
	String username=null;
	String jwt=null;
	
	if(header!=null)
	{
		jwt=header;
		username=utility.getUsernameFromToken(jwt);
	}
	if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
	{
		UserDetails userdetails=user.loadUserByUsername(username);
		if(utility.validateToken(jwt,userdetails))
		{
			UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
			upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(upat);
		}
	}
	filterChain.doFilter(request, response);
	}

	
	
}
