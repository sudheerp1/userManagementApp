package com.userManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginHandler implements AuthenticationSuccessHandler {

	@Autowired
	JwtUtil jwtService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException, java.io.IOException {
		// Generate the JWT token
		String jwtToken = jwtService.generateToken(authentication);

		// Add the JWT token to the Authorization header
		response.setHeader("Authorization", "Bearer " + jwtToken);

		log.info("JWT token added to the header and login response sent successfully.");
	}

}