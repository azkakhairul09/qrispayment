package com.sangbango.project.security;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangbango.project.SpringApplicationContext;
import com.sangbango.project.exceptions.UserServiceException;
import com.sangbango.project.services.impl.UserServiceImpl;
import com.sangbango.project.shared.dto.CredsDto;
import com.sangbango.project.shared.dto.UserDto;
import com.sangbango.project.ui.entitymodel.UserEntity;
import com.sangbango.project.ui.entitymodel.request.LoginRequest;
import com.sangbango.project.ui.repositories.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		try {
			LoginRequest login = new ObjectMapper()
					.readValue(req.getInputStream(), LoginRequest.class);
			
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							login.getEmail(),
							login.getPassword(),
							new ArrayList<>()));
			
		} catch (IOException e) {
			throw new UserServiceException("wrong email or password");
		}
	}
	
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) throws IOException, ServletException {
//		get login data
		String email = ((User) auth.getPrincipal()).getUsername();
		
		String token = Jwts.builder()
		.setSubject(email)
		.setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
		.signWith(SignatureAlgorithm.HS512, SecurityConstant.getTokenSecret())
		.compact();
		
		UserServiceImpl userService = (UserServiceImpl) SpringApplicationContext.getBean("userServiceImpl");
		
		UserRepository userRepository = (UserRepository) SpringApplicationContext.getBean("userRepository");
		
		UserDto userDto = userService.getUser(email);
		
//		create response header
		res.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
		res.addHeader("userID", userDto.getUserId());
		
//		create login status
		UserEntity userEntity = userRepository.findUserByUserId(userDto.getUserId());
		
		final String CREATED_DATE = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(CREATED_DATE);
		format.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		
		Calendar createdTime = Calendar.getInstance();
		
		String loginTime = format.format(createdTime.getTime());
		userEntity.setLoginTime(loginTime);
		
//		save detail login
		userRepository.save(userEntity);
		
//		creating response object
		UserEntity userRole = userRepository.findRoleByUserId(userDto.getUserId());
		
		String JWT= (SecurityConstant.TOKEN_PREFIX + token);
		String userID = userDto.getUserId();
		String userImage = userDto.getUserImage();
		String name = userDto.getName();
		String role = userRole.getRole().getRoleName();
		
		CredsDto creds = new CredsDto();
		creds.setStatus("1");
		creds.setDescription("logged in");
		creds.setToken(JWT);
		creds.setUserId(userID);
		creds.setName(name);
		creds.setUserImage(userImage);
		creds.setRole(role);
		creds.setEmail(email);
		Date date = new Date();
		creds.setTimestamp(new Timestamp(date.getTime()).toString());
		
		// Creating Object of ObjectMapper define in Jakson Api 
		ObjectMapper Obj = new ObjectMapper(); 
		
		try 
		{ 
			// set object as a json string 
			String response = Obj.writeValueAsString(creds);
			
			res.setStatus(HttpServletResponse.SC_OK);
			res.setContentType("application/json");
			res.getWriter().println(response);
			res.getWriter().flush();
			res.getWriter().close();
			
			//System.out.println(response);
		} 
			catch (IOException e) 
		{ 
			e.printStackTrace(); 
		}       
	}
}
