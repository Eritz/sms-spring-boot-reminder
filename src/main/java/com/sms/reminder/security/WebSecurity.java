// https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
package com.sms.reminder.security;

import static com.sms.reminder.security.SecurityConstants.SIGN_UP_URL;
import static com.sms.reminder.security.SecurityConstants.NOTIFICATIONS_URL;
import static com.sms.reminder.security.SecurityConstants.LOGIN_URL;
import static com.sms.reminder.security.SecurityConstants.LOGOUT_URL;
import static com.sms.reminder.security.SecurityConstants.MY_ACCOUNT_URL;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Added onto Spring Security's web security configuration 
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
///////////// Made "/register" as public and everything else as being secured
	// added CORS support and custom security filter
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
			.antMatchers(HttpMethod.POST, NOTIFICATIONS_URL).permitAll()
			.antMatchers(HttpMethod.GET, NOTIFICATIONS_URL).hasAuthority("ADMIN")
			.antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
			.antMatchers(HttpMethod.GET, LOGOUT_URL).authenticated()
			.antMatchers(HttpMethod.GET, MY_ACCOUNT_URL).authenticated()
			.anyRequest().permitAll()
			.and()
			// this config is for Basic Authentication, so the below code will not work.
			//.logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL)).logoutSuccessUrl(LOGIN_URL)
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// make Spring Security Session stateless - i.e, no session will be created by Spring Security
	}
	
	// Load user-specific data in the security framework and encrypt it
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	// Allow CORS support from any source
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
}
