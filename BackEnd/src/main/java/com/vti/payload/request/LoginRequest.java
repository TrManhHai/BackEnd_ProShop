package com.vti.payload.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.vti.entity.account.EStatus;

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	private String email;

	private List<String> role;

	private EStatus EStatus;

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public EStatus getStatus() {
		return EStatus;
	}

	public void setStatus(EStatus EStatus) {
		this.EStatus = EStatus;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
