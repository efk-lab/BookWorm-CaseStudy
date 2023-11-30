package com.bookworm.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bookworm.conf.security.UserService;

public abstract class BaseControllerImpl {
	
	@Autowired
	protected UserService userService;
	
}
