package com.userManagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.userManagement.dto.UserDto;
import com.userManagement.security.JwtUtil;
import com.userManagement.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AuthenticationManager authenticationManager;

	@MockitoBean
	private JwtUtil jwtUtil;

	@MockitoBean
	private UserService userService;

	@Test
	void testRegisterUser_Success() throws Exception {
		UserDto userDto = new UserDto("John", "Doe", "john.doe@example.com", "1234567890", "password");
		when(userService.registerUser(any(UserDto.class))).thenReturn(userDto);

		mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content("""
				{
				    "firstName": "John",
				    "lastName": "Doe",
				    "email": "john.doe@example.com",
				    "phoneNumber": "1234567890",
				    "password": "password"
				}
				""")).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.phoneNumber").value("1234567890"));
	}

	@Test
	void testDeactivateUser_Success() throws Exception {
		long userId = 1L;
		when(userService.deactivateUser(userId)).thenReturn("deactivated successfully");

		mockMvc.perform(patch("/api/user/deactivate/{id}", userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("deactivated successfully"));
	}

	@Test
	void testDeleteUser_Success() throws Exception {
		long userId = 1L;
		when(userService.deleteUser(userId)).thenReturn("user deleted successfully");

		mockMvc.perform(patch("/api/user/delete/{id}", userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string("user deleted successfully"));
	}

	@Test
	void testLogin_Failure() throws Exception {
		when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));

		mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content("""
				{
				    "email": "john.doe@example.com",
				    "password": "wrongpassword"
				}
				""")).andExpect(status().isUnauthorized())
				.andExpect(content().string("Login failed: Invalid credentials"));
	}

	@Test
	void testTestEndpoint() throws Exception {
		mockMvc.perform(get("/api/user/test")).andExpect(status().isOk()).andExpect(content().string("i am up"));
	}
}
