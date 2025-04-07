package com.userManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "myUser")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "firstName must be provided")
	private String firstName;
	@NotBlank(message = "lastName must be provided")
	private String lastName;
	@NotBlank(message = "email must be provided")
	private String email;
	@NotBlank(message = "phoneNumber must be provided")
	private String phoneNumber;
	@NotBlank(message = "password must be provided")
	private String password;
	@Column(nullable = false)
	@Builder.Default
	private boolean enabled = true;

}
