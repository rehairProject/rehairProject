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

    @GetMapping("/reservation_check")
    public String reservationCheck() {
        return "client/reservation_check";
    }

    @GetMapping("/event_detail")
    public String eventDetail() {
        return "client/event_detail";
    }

    @GetMapping("/event")
    public String event() {
        return "client/event";
    }
}
