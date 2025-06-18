package com.tracker.backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.backend.entities.User;
import com.tracker.backend.exceptions.ApiException;
import com.tracker.backend.payloads.JwtAuthRequest;
import com.tracker.backend.payloads.JwtAuthResponse;
import com.tracker.backend.payloads.UserDto;
import com.tracker.backend.security.JwtTokenHelper;
import com.tracker.backend.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager autheticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response= new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.autheticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (AuthenticationException e) {
			System.err.println("Invalid Details username or password"+ e);
			throw new ApiException("Invalid Credentials");
		}
		
	}
	
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
}
