package com.tracker.backend.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tracker.backend.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	@NotEmpty
	@Size(min=3, message= "Name must be of minimum 3 characters")
	private String name;
	
	@Email(message="Provided email address is not valid")
	@NotBlank
	private String email;

	@NotEmpty
	@Size(min=6, message="Password must be more than 5 characters")
	private String password;
	
	@NotNull
	private String about;
	
	private Set<Role> role= new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
