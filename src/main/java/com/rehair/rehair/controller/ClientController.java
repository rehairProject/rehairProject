package com.rehair.rehair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/about")
    public String about() {
        return "client/about";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "client/reservation";
    }

    @GetMapping("/notice")
    public String notice() {
        return "client/notice";
    }

    @GetMapping("/reservationChk")
    public String reservation_check() {
        return "client/reservation_check";
    }

    @GetMapping("/eventDetail")
    public String eventDetail() {
        return "client/eventDetail";
    }

    @GetMapping("/membership")
    public String membership() {
        return "client/membership";
    }
}
