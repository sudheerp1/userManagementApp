package com.userManagement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userManagement.entity.User;
import com.userManagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
		User user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for this: " + emailOrPhone));
		return new CustomUserDetails(user);
	}
}
