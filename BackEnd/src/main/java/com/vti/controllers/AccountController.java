package com.vti.controllers;

import com.vti.payload.dto.AccountDTO;
import com.vti.entity.account.Account;
import com.vti.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private IAccountService accountService;

	@GetMapping(value = "/email/{email}")
	public ResponseEntity<?> existsUserByEmail(@PathVariable(name = "email") String email) {
		// get entity
		boolean result = accountService.existsUserByEmail(email);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/username/{username}")
	public ResponseEntity<?> existsUserByUserName(@PathVariable(name = "username") String username) {
		// get entity
		boolean result = accountService.existsUserByUsername(username);

		// return result
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping()

	public ResponseEntity<?> getAllaccount(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Account> accountPage_DB = accountService.getAllAccounts(pageable, search);
		// Dữ liệu lấy ở DB, đã được thực hiện phân trang và sort dữ liệu

		// Chuyển đổi dữ liệu
		Page<AccountDTO> accountPage_Dtos = accountPage_DB.map(new Function<Account, AccountDTO>() {
			@Override
			public AccountDTO apply(Account account) {
				AccountDTO AccountDTO = new AccountDTO();
				AccountDTO.setId(account.getId());
				AccountDTO.setEmail(account.getEmail());
				AccountDTO.setUsername(account.getUsername());
				AccountDTO.setRole(account.getRoles());
				AccountDTO.setStatus(account.getStatus());
				return AccountDTO;
			}

		});

		return new ResponseEntity<>(accountPage_Dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccountByID(@PathVariable(name = "id") Long id) {
		try {
			Account accountDB = accountService.getAccountByID(id);

			// convert accountDB --> AccountDTO

			AccountDTO AccountDTO = new AccountDTO();
			AccountDTO.setId(accountDB.getId());
			AccountDTO.setEmail(accountDB.getEmail());
			AccountDTO.setUsername(accountDB.getUsername());
			AccountDTO.setRole(accountDB.getRoles());
			AccountDTO.setStatus(accountDB.getStatus());
			return new ResponseEntity<>(AccountDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

	}
	@PutMapping(value = "/block/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<?> upBlockExpDate(@PathVariable(name = "id") long id,@RequestParam(name = "blockExpDate") Integer date){
		accountService.upBlockExpDate(id,date);
	return null;
	}
	@PutMapping(value = "/unblock/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public ResponseEntity<?> unBlockExpDate(@PathVariable(name = "id") long id){
		accountService.unBlockExpDate(id);
		return null;
	}

	@GetMapping(value = "/avatar/{id}")
	public ResponseEntity<?> getAvatar(@PathVariable(name = "id") long id) {
		HttpHeaders headers = new HttpHeaders();
//        headers.add("content-disposition", "inline;filename=" + fileName[fileName.length - 1]);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.IMAGE_JPEG)
				.body(accountService.getAvatar(id));
	}

	@PostMapping(value = "/upload-avatar/{id}")
	public ResponseEntity<?> uploadAvatar(@PathVariable(name = "id") long id, @RequestParam MultipartFile file) {
		accountService.uploadAvatar(id, file);
		return ResponseEntity.ok()
				.body("ok");
	}

}
