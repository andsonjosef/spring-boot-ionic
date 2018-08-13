package com.example.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.cursomc.security.UserSS;

public class UserService {
	public static UserSS authemticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

	}

}
