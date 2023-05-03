package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vti.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
	Account findByUsername(String username);

	public boolean existsByUsername(String username);

	public boolean existsByEmail(String email);

	public Account findByEmail(String email);
}
