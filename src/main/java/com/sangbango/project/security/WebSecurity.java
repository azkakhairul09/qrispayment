package com.sangbango.project.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sangbango.project.services.UserService;
import com.sangbango.project.ui.repositories.UserRepository;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	
	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstant.SIGN_UP)
			.permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstant.LOGIN)
			.permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstant.ROLE)
			.permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstant.QREN_NOTIF)
			.permitAll()
			.antMatchers(HttpMethod.GET, SecurityConstant.HELLOWORLD)
			.permitAll()		
			.antMatchers(HttpMethod.POST, SecurityConstant.UPLOAD)
			.permitAll()
			.antMatchers(HttpMethod.GET, SecurityConstant.SOME_PRODUCTS)
			.permitAll()
			.antMatchers(HttpMethod.GET, SecurityConstant.PRODUCTS)
			.permitAll()
			.antMatchers(HttpMethod.GET, SecurityConstant.PRODUCT_DETAIL)
			.permitAll()
			.anyRequest().authenticated().and() 
			.addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager(), userRepository))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/login");
		
		return filter;
	}
	
	@Bean
	public CorsConfigurationSource configurationSource() {
		
		final CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
}
