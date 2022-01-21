package com.rehair.rehair.controller;

import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/notice")
    public String notice() {
        return "notice";
    }

    @GetMapping("/reservationChk")
    public String reservation_check() {
        return "reservation_check";
    }

    @GetMapping("/eventDetail")
    public String eventDetail() {
        return "eventDetail";
    }

    @GetMapping("/membership")
    public String membership() {
        return "membership";
    }

}
