package com.bookworm.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookworm.conf.security.UserService;

public abstract class BaseService {
	
	@Autowired
	protected UserService userService;
	
}
