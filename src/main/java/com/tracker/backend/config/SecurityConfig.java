package com.tracker.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tracker.backend.security.CustomUserDetailService;
import com.tracker.backend.security.JwtAuthenticationEntryPoint;
import com.tracker.backend.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
						.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated())
				.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and().sessionManagement(
						sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		//.requestMatchers(HttpMethod.GET).permitAll()
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public FilterRegistrationBean<?> coresFilter(){
		UrlBasedCorsConfigurationSource urlBasedCorsConfigSource = new UrlBasedCorsConfigurationSource();
		
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedOriginPattern("*");
		corsConfig.addAllowedHeader("Authorization");
		corsConfig.addAllowedHeader("Content-Type");
		corsConfig.addAllowedHeader("Accept");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("DELETE");
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedMethod("OPTIONS");
		corsConfig.setMaxAge(3600L);
		
		
		urlBasedCorsConfigSource.registerCorsConfiguration("/**", corsConfig);
		
		FilterRegistrationBean<?> bean = new FilterRegistrationBean<>(new CorsFilter(urlBasedCorsConfigSource));
		bean.setOrder(-110);
		return bean;
	}
}
