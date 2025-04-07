package com.userManagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userManagement.dto.UserDto;
import com.userManagement.entity.User;
import com.userManagement.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDto registerUser(UserDto userDto) {
		// Check if email or phone number already exists
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new IllegalArgumentException("Email is already registered!");
		}
		if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
			throw new IllegalArgumentException("Phone number is already registered!");
		}

		// Convert DTO to Entity
		User user = User.builder().firstName(userDto.getFirstName()).lastName(userDto.getLastName())
				.email(userDto.getEmail()).phoneNumber(userDto.getPhoneNumber())
				.password(passwordEncoder.encode(userDto.getPassword())) // Encrypt password
				.build();
		userRepository.save(user);

		// Save user in the database
		return userDto;
	}
}
