package com.tracker.backend.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tracker.backend.config.AppConstants;
import com.tracker.backend.entities.Role;
import com.tracker.backend.entities.User;
import com.tracker.backend.payloads.UserDto;
import com.tracker.backend.repositories.RoleRepository;
import com.tracker.backend.repositories.UserRepo;
import com.tracker.backend.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	UserRepo userRepo;
	
	@Mock
	RoleRepository roleRepo;

	@Mock
	ModelMapper modelMapper;
	@Mock
	PasswordEncoder passwordEncoder;

	ModelMapper mapper = new ModelMapper();

	@InjectMocks
	UserServiceImpl userService;
	
	@Test
	void testRegisterNewUserr() {
		UserDto userDto= new UserDto();
		userDto.setName("John");
		userDto.setPassword("TestPass");
		userDto.setEmail("email@gmail.com");
		
		User user = this.mapper.map(userDto, User.class);
		
		Role role= new Role();
		role.setId(2);
		role.setName("NORMAL");
		
		User savedUser= user;
		savedUser.getRole().add(role);
		
		UserDto savedUserDto = this.mapper.map(savedUser, UserDto.class);
		
		Mockito.when(modelMapper.map(userDto, User.class)).thenReturn(user);
		Mockito.when(roleRepo.findById(AppConstants.NORMAL_USER)).thenReturn(Optional.of(role));
		Mockito.when(userRepo.save(any(User.class))).thenReturn(savedUser);
		Mockito.when(modelMapper.map(savedUser, UserDto.class)).thenReturn(savedUserDto);
		
		UserDto returnedUserDto =userService.registerNewUser(userDto);
		
		Assertions.assertEquals("John", returnedUserDto.getName());
		
	}
}
