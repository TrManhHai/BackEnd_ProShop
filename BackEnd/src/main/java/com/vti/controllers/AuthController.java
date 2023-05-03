package com.vti.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.vti.exceptions.AppException;
import com.vti.exceptions.ErrorResponseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.vti.entity.account.Account;
import com.vti.entity.account.ERole;
import com.vti.entity.account.Role;
import com.vti.payload.request.LoginRequest;
import com.vti.payload.request.SignupRequest;
import com.vti.payload.response.MessageResponse;
import com.vti.payload.response.UserInfoResponse;
import com.vti.repository.AccountRepository;
import com.vti.repository.RoleRepository;
import com.vti.security.jwt.JwtUtils;
import com.vti.services.AccountDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")

	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		AccountDetailsImpl userDetails;
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			userDetails = (AccountDetailsImpl) authentication.getPrincipal();

		} catch (Exception ex){
			throw new AppException(ex);
		}
		if (userDetails.getBlockExpDate() != null && userDetails.getBlockExpDate().compareTo(new Date()) > 0 ){
			throw new AppException(ErrorResponseBase.USER_BLOCKED);
		}
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		String jwt = jwtUtils.generateJwtToken(authentication);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),
						roles, userDetails.getEStatus()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Account account = new Account(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		List<String> strRoles = signUpRequest.getRole();
		List<Role> roles = new ArrayList<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
					roles.add(adminRole);

					break;
				case "MOD":
					Role modRole = roleRepository.findByName(ERole.MANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Manager Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.USER)
							.orElseThrow(() -> new RuntimeException("Error: User Role is not found."));
					roles.add(userRole);
				}
			});
		}

		account.setRoles(roles);
		accountRepository.save(account);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.clearJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("You've been signed out!"));
	}
}
