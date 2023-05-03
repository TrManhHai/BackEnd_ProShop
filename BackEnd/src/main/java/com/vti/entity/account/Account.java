package com.vti.entity.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Account", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Account implements Serializable {
	@Column(name = "accountID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Column(name = "url_avatar", length = 100)
	private String urlAvatar;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Column
	private Date blockExpDate; // mốc thời gian user bị khoá

	@ManyToMany(fetch = FetchType.LAZY,cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(name = "account_roles",
			joinColumns = { @JoinColumn(name = "account_ID") },
			inverseJoinColumns = { @JoinColumn(name = "role_ID") })
	private List<Role> roles;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "`status`", nullable = false)
	private EStatus status = EStatus.ACTIVE;

	public Account(String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

}
