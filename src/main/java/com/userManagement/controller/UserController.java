package com.userManagement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userManagement.dto.UserDto;
import com.userManagement.security.JwtUtil;
import com.userManagement.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto userDto) {
		Authentication authentication = null;
		try {
			String userName = userDto.getEmail() != null ? userDto.getEmail() : userDto.getPhoneNumber();
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, userDto.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Login failed: " + e.getMessage());
		}

		String token = jwtUtil.generateToken(authentication);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		return ResponseEntity.ok().headers(headers).body("logged in successfully");
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userDTO));
	}

	@PatchMapping("/update")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(userDTO));
	}

	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<String> deactivateUser(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deactivateUser(id));
	}

	@PatchMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("i am up");
	}
}