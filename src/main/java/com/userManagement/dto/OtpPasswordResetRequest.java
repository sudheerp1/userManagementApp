package com.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OtpPasswordResetRequest {

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "OTP is required")
	@Size(min = 6, max = 6, message = "OTP must be 6 digits")
	private String otp;

	@NotBlank(message = "New password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String newPassword;

	// Getters and Setters
}
