package com.rehair.rehair.controller;

import com.rehair.rehair.domain.User;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final UserService userService;

	@GetMapping("/login")
	public String login(@RequestParam(required = false) boolean isSuccess, Model model) {
		if (isSuccess){
			model.addAttribute("isSuccess", true);
		}
		return "account/login";
	}

	@GetMapping("/join")
	public String joinForm() {
		return "account/join";
	}

	@PostMapping("/join")
	public String join(User user, Model model, RedirectAttributes redirectAttributes){
		if ((ObjectUtils.isEmpty(user.getUsername())) || ObjectUtils.isEmpty(user.getPassword()) || ObjectUtils.isEmpty(user.getName()) || ObjectUtils.isEmpty(user.getPhoneNumber()) || ObjectUtils.isEmpty(user.getBirth()) || ObjectUtils.isEmpty(user.getGender())){
			String msg = "회원정보에 누락된 곳이 있습니다. 확인 후 기입하시기 바랍니다.";
			model.addAttribute("msg", msg);
			return "account/join";
		}

		User newUser = userService.joinUser(user);
		redirectAttributes.addFlashAttribute("isSuccess", true);
		return "redirect:/account/login";
	}
}
