package com.rehair.rehair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class UserController {

    @GetMapping("/join")
    public String join() {
        return "account/join";
    }
}
