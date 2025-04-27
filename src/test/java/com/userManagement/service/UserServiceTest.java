package com.userManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.userManagement.dto.UserDto;
import com.userManagement.entity.User;
import com.userManagement.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterUser_WhenEmailAlreadyExists_ShouldThrowException() {
		UserDto userDto = new UserDto("John", "Doe", "john@example.com", "1234567890", "password");
		when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.registerUser(userDto));

		assertEquals("Email is already registered!", exception.getMessage());
	}

	@Test
	void testRegisterUser_WhenPhoneNumberAlreadyExists_ShouldThrowException() {
		UserDto userDto = new UserDto("Jane", "Doe", "jane@example.com", "1234567890", "password");
		when(userRepository.existsByPhoneNumber("1234567890")).thenReturn(true);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.registerUser(userDto));

		assertEquals("Phone number is already registered!", exception.getMessage());
	}

	@Test
	void testDeleteUser_WhenUserExists_ShouldDeleteUser() {
		long userId = 1L;
		User user = new User();
		user.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String response = userService.deleteUser(userId);

		verify(userRepository).delete(user);
		assertEquals("user deleted successfully", response);
	}

	@Test
	void testDeleteUser_WhenUserDoesNotExist_ShouldThrowException() {
		long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> userService.deleteUser(userId));

		assertEquals("user not Found", exception.getMessage());
	}

	@Test
	void testDeactivateUser_WhenUserExists_ShouldDeactivateUser() {
		long userId = 2L;
		User user = new User();
		user.setId(userId);
		user.setEnabled(true);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		String response = userService.deactivateUser(userId);

		assertFalse(user.isEnabled());
		verify(userRepository).save(user);
		assertEquals("deactivated successfully", response);
	}

	@Test
	void testDeactivateUser_WhenUserDoesNotExist_ShouldThrowException() {
		long userId = 2L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> userService.deactivateUser(userId));

		assertEquals("user not found", exception.getMessage());
	}
}
