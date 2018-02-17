// https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
// Boiler plate JWT authentication and token code

package com.sms.reminder.security;

import static com.sms.reminder.security.SecurityConstants.EXPIRATION_TIME;
import static com.sms.reminder.security.SecurityConstants.HEADER_STRING;
import static com.sms.reminder.security.SecurityConstants.SECRET;
import static com.sms.reminder.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// This will be invoked at the pre-processing and post-processing of a request
// In this case, this filter will authenticate and give a user a JWT token
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	// Attempts parsing user credentials and gives it to the authentication manager
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		try {
			com.sms.reminder.user.User creds = new ObjectMapper().readValue(req.getInputStream(), com.sms.reminder.user.User.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					creds.getUsername(),
					creds.getPassword(),
					new ArrayList<>())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// On successful login, give the user a JWT by the Jwts class
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
					HttpServletResponse res, FilterChain chain,
					Authentication auth) throws IOException, ServletException {
		
		String token = Jwts.builder()
						.setSubject(((User) auth.getPrincipal()).getUsername())
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
						.compact();
		res.addHeader(HEADER_STRING,  TOKEN_PREFIX + token);
								
	}
			
}
