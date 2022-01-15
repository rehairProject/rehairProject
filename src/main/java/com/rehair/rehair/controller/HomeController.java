package com.rehair.rehair.controller;

import com.rehair.rehair.domain.ReservationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String reservation() {return "reservation";}

    @PostMapping("/reservation")
    public String reservationForm(ReservationForm form){
        ReservationInfo info=new ReservationInfo();
        info.setDesigner(form.getDesigner());
        info.setProcedure(form.getProcedure());
        return "/reservationChk";
    }


    @GetMapping("/notice")
    public String notice() {
        return "notice";
    }

    @GetMapping("/reservationChk")
    public String reservation_check(Model model) {

        return "reservation_check";
    }

}
