package com.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailRequest {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	// Getter and Setter
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
