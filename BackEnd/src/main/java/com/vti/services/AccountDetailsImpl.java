package com.vti.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.vti.entity.account.EStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vti.entity.account.Account;

@Data
public class AccountDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private EStatus EStatus;

	private Date blockExpDate;

	private Collection<? extends GrantedAuthority> authorities;

	public AccountDetailsImpl(Long id, String username, String email, String password, EStatus EStatus,
			Collection<? extends GrantedAuthority> authorities, Date blockExpDate) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.EStatus = EStatus;
		this.blockExpDate = blockExpDate;
	}

	public static AccountDetailsImpl build(Account account) {
		List<GrantedAuthority> authorities = account.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new AccountDetailsImpl(account.getId(), account.getUsername(), account.getEmail(), account.getPassword(),
				account.getStatus(), authorities, account.getBlockExpDate());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public EStatus getEStatus() {
		return EStatus;
	}

	public void setEStatus(EStatus EStatus) {
		this.EStatus = EStatus;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		AccountDetailsImpl account = (AccountDetailsImpl) o;
		return Objects.equals(id, account.id);
	}
}
