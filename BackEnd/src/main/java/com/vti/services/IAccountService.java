package com.vti.services;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.vti.entity.account.Account;
import org.springframework.web.multipart.MultipartFile;

public interface IAccountService {
	public Page<Account> getAllAccounts(Pageable pageable, String search);

	public Account getAccountByID(Long id);

	public Account findByUsername(String username);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	boolean existsUserByEmail(String email);

	boolean existsUserByUsername(String userName);

    public void upBlockExpDate(long id, Integer date);

	public void unBlockExpDate(long id);

	ByteArrayResource getAvatar(long id);

    void uploadAvatar(long id, MultipartFile file);
}
