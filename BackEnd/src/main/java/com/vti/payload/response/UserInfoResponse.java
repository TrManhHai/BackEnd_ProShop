package com.vti.payload.response;

import java.util.List;

import com.vti.entity.account.EStatus;

public class UserInfoResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private EStatus EStatus;

	public UserInfoResponse(String accessToken, Long id, String username, String email, List<String> roles,
			EStatus EStatus) {
		super();
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.EStatus = EStatus;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EStatus getStatus() {
		return EStatus;
	}

	public void setStatus(EStatus EStatus) {
		this.EStatus = EStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}