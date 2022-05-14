package com.rehair.rehair.controller;

import com.rehair.rehair.domain.HolidayStatus;
import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.Schedule;
import com.rehair.rehair.repository.ReservationRepository;
import com.rehair.rehair.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ScheduleRepository scheduleRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin(@RequestParam(required = false) String reservationYear, @RequestParam(required = false) String reservationMonth, Model model){
    	String day = "";
    	
    	LocalDate now = LocalDate.now();
    	model.addAttribute("nowYear", now.toString().substring(0, 4));
		
		if(reservationYear == null && reservationMonth == null) {
			day = now.toString().substring(0, 7);;
		}else {
			day = reservationYear + "-" + reservationMonth;
		}
		
		List<Reservation> reservations = reservationRepository.findByDayContaining(day);
		model.addAttribute("reservations", reservations);
    	
        List<Schedule> schedules = scheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        return "admin";
    }

    @PostMapping("/admin/holiday")
    public String holiday(@RequestParam String date, @RequestParam String designer){
        //DB에 휴무 직원 저장
        Schedule schedule = scheduleRepository.getById(date);
        schedule.setStatus(HolidayStatus.nameOf(designer));
        scheduleRepository.save(schedule);
        return "redirect:/admin";
    }

    @GetMapping("admin/holiday/{date}")
    public @ResponseBody String holidayDesigner(@PathVariable String date){
        Schedule schedule = scheduleRepository.getById(date);
        String holidayDesigner;
        if(ObjectUtils.isEmpty(schedule.getStatus())){
            holidayDesigner = "";
        } else {
            HolidayStatus holidayStatus = schedule.getStatus();
            holidayDesigner = holidayStatus.getName();
        }
        return holidayDesigner;
    }
}
