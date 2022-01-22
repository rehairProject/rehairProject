package com.rehair.rehair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/section")
public class SectionController {

    @GetMapping("/about")
    public String about() {
        return "section/about";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "section/reservation";
    }

    @GetMapping("/notice")
    public String notice() {
        return "section/notice";
    }

    @GetMapping("/reservationChk")
    public String reservation_check() {
        return "section/reservation_check";
    }

    @GetMapping("/eventDetail")
    public String eventDetail() {
        return "section/eventDetail";
    }

    @GetMapping("/membership")
    public String membership() {
        return "section/membership";
    }
}
