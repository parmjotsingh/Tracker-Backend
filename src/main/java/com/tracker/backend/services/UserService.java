package com.tracker.backend.services;

import java.util.List;

import com.tracker.backend.payloads.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
