package com.tracker.backend.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
	private String token;
	private UserDto user;
}
