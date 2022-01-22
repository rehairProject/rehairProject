package com.rehair.rehair.controller;

import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

}
