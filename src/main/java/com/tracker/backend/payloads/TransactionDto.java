package com.tracker.backend.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionDto {
	private Integer entryId;
	
	@NotNull(message="Date cannot be null")
	@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message="Date format incorrect. Should be [yyyy-mm-dd]")
	private String date;
	
	@NotBlank
	@Size(min= 1, max=100, message="Description must be in 1 to 100 letters")
	private String description;
	
	@NotNull(message="Amount cannot be null")
	@Min(value = 0, message="Negative amounts are not allowed. Choose Transaction type \"debit\" to show deductions.")
	private Long amount;
	
	@NotBlank(message="Type value is required")
	@Size(min=5, max=6, message="Accepted values are \"credit\" or \"debit\"")
	@Pattern(regexp = "^(?i)(credit|debit)$", message="Transaction type mismatch. Accepted values are \"credit\" or \"debit\"")
	private String type;
	
	private UserDto user;
}
