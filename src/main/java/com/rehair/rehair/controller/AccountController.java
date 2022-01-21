package com.rehair.rehair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
