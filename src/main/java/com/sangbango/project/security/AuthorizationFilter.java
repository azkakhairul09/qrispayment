package com.sangbango.project.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.sangbango.project.ui.repositories.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	public AuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws ServletException, IOException {
		String header = req.getHeader(SecurityConstant.HEADER_STRING);
		
		if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstant.HEADER_STRING);
        
        if (token != null) {
            try {
	            token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
	            
	            String user = Jwts.parser()
	                    .setSigningKey( SecurityConstant.getTokenSecret())
	                    .parseClaimsJws( token )
	                    .getBody()
	                    .getSubject();
	            
	            if (user != null) {
	                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	            }
            }
            catch (ExpiredJwtException ex) {
				// TODO: handle exception
            	System.out.println(ex.getMessage());
			}
            
            return null;
        }
        
        return null;
    }
}
