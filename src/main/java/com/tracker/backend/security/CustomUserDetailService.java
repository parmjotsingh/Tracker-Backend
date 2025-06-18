package com.tracker.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tracker.backend.entities.User;
import com.tracker.backend.exceptions.ResourceNotFoundException;
import com.tracker.backend.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Username", "email", username));
		return user;
	}

}
