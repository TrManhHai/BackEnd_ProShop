package com.vti.services;

import com.vti.entity.account.Account;
import com.vti.exceptions.AppException;
import com.vti.exceptions.ErrorResponseBase;
import com.vti.repository.AccountRepository;
import com.vti.specification.AccountSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} else {
			return AccountDetailsImpl.build(account);
		}
	}

	@Override
	public Page<Account> getAllAccounts(Pageable pageable, String search) {
		Specification<Account> whereAccount = null;
		if (!StringUtils.isEmpty(search)) {
			AccountSpecification usernameSpecification = new AccountSpecification("username", "LIKE", search);
			AccountSpecification roleSpecification = new AccountSpecification("role", "LIKE", search);
			AccountSpecification emailSpecification = new AccountSpecification("email", "LIKE", search);
			whereAccount = Specification.where(usernameSpecification).or(roleSpecification).or(emailSpecification);
		}

		return accountRepository.findAll(whereAccount, pageable); // findAll - phuong thuc co san cua JPA da duoc xay
																	// dung san khi extends ben repository
	}

	@Override
	public Account getAccountByID(Long id) {
		return accountRepository.getById(id);
	}

	@Override
	public Account findByUsername(String username) {
		// TODO Auto-generated method stub
		return accountRepository.findByUsername(username);
	}

	@Override
	public boolean existsUserByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.existsByEmail(email);
	}

	@Override
	public boolean existsUserByUsername(String username) {
		// TODO Auto-generated method stub
		return accountRepository.existsByUsername(username);
	}

	@Override
	public void upBlockExpDate(long id, Integer date) {
		Account account = accountRepository.getById(id);
		Date dateBlock = addSeconds(new Date(), date);
		account.setBlockExpDate(dateBlock);
		accountRepository.save(account);

	}

	@Override
	public void unBlockExpDate(long id) {
		Account account = accountRepository.getById(id);
		account.setBlockExpDate(null);
		accountRepository.save(account);
	}

	@Override
	public ByteArrayResource getAvatar(long id) {
		File file;

		Optional<Account> optional = accountRepository.findById(id);
		if (!optional.isPresent()) {
			throw new AppException(ErrorResponseBase.NOT_FOUND);
		}
		Account account = optional.get();
		if (StringUtils.isEmpty(account.getUrlAvatar())) {
			file = new File("src/main/resources/avatar/default.png");
		} else {
			file = new File("src/main/resources/" + account.getUrlAvatar());
		}
		try {
			byte[] templateContent = FileCopyUtils.copyToByteArray(file);
			return new ByteArrayResource(templateContent);
		} catch (Exception ex) {
			throw new AppException(ex);
		}
	}

	@Override
	public void uploadAvatar(long id, MultipartFile file) {
		File saveFile;
		Optional<Account> optional = accountRepository.findById(id);
		if (!optional.isPresent()) {
			throw new AppException(ErrorResponseBase.NOT_FOUND);
		}
		Account account = optional.get();
		String urlAvatar = "avatar/" + getNameFile(id, file);

		if (!StringUtils.isEmpty(account.getUrlAvatar())) {
			saveFile = new File("src/main/resources/" + account.getUrlAvatar());
			if (saveFile.exists()) {
				saveFile.delete();
			}
		}
		saveAvatar(urlAvatar, file);
		account.setUrlAvatar(urlAvatar);
		accountRepository.save(account);
	}

	private void saveAvatar(String name, MultipartFile file) {
		File saveFile = new File("src/main/resources/" + name);
		try {
			OutputStream os = new FileOutputStream(saveFile);
			os.write(file.getBytes());
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	private String getNameFile(long id, MultipartFile file) {
		String[] fileName = file.getOriginalFilename().split("\\.");
		return (id + "." + fileName[fileName.length - 1]);
	}


	public static Date addSeconds(Date date, Integer seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
}
