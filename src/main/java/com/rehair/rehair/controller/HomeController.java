package com.rehair.rehair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/event")
    public String event() {
        return "event";
    }

    @GetMapping("/reservationChk")
    public String reservation_check() {
        return "reservation_check";
    }

}
