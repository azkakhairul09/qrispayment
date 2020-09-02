package com.sangbango.project.shared;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.sangbango.project.security.SecurityConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class Utils {
	private final Random RANDOM = new SecureRandom();
	private final String NUMBER = "0123456789";
	
	public String generateUserId(int length) {
		return generateRandomString(length);
	}
	
	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		
		for (int i = 0; i < length; i++) {
			returnValue.append(NUMBER.charAt(RANDOM.nextInt(NUMBER.length())));
		}
		
		return new String(returnValue);
	}
	
	public static boolean hasTokenExpired(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstant.getTokenSecret())
				.parseClaimsJws(token).getBody();
		
		Date tokenExpirationDate = claims.getExpiration();
		Date todayDate = new Date();
		
		return tokenExpirationDate.before(todayDate);
	}
	
	public String generateId(int length) {
		// TODO Auto-generated method stub
		return generateRandomString(length);
	}
}
