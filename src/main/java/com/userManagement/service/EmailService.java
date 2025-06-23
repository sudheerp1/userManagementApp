package com.userManagement.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Async
	public void sendPasswordResetOtp(String toEmail, String otp) {
		String subject = "Password Reset OTP";
		String body = "Your OTP to reset your password is: " + otp + "\n\n" + "It will expire in 10 minutes.";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
}
