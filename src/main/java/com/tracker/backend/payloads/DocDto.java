package com.tracker.backend.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DocDto {
	@NotEmpty(message = "Please send data. Empty not allowed")
	private String data;
	
	private String entryId;
}
