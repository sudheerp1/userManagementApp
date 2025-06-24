package com.userManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {

	@NotBlank(message = "New password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String newPassword;

	// getters and setters
}