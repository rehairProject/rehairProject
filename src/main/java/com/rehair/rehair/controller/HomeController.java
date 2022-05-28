package com.rehair.rehair.controller;

import com.rehair.rehair.domain.HolidayStatus;
import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.Schedule;
import com.rehair.rehair.repository.ReservationRepository;
import com.rehair.rehair.repository.ScheduleRepository;
import com.rehair.rehair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin(@RequestParam(required = false) String reservationYear, @RequestParam(required = false) String reservationMonth, @RequestParam(required = false, value = "tabFlag") String tabFlag, Model model){
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
        model.addAttribute("tabFlag", tabFlag);
        if (tabFlag == null){
            model.addAttribute("tabFlag", "null");
        }
        return "admin";
    }

    @PostMapping("/admin/holiday")
    public String holiday(@RequestParam String date, @RequestParam(required = false) String designer){
        Schedule schedule = scheduleRepository.getById(date);
        schedule.setStatus(HolidayStatus.nameOf(designer));
        scheduleRepository.save(schedule);
        return "redirect:/admin?tabFlag=save";
    }

    @GetMapping("/admin/holiday/{date}")
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

    @PostMapping("/admin/holiday/{date}")
    public String holidayDelete(@PathVariable String date){
        System.out.println("휴무삭제컨트롤러");
        Schedule schedule = scheduleRepository.getById(date);
        schedule.setStatus(null);
        scheduleRepository.save(schedule);
        return "redirect:/admin?tabFlag=del";
    }

    /* 디자이너 */
    @GetMapping("/designer")
    public String designer(){
        return "designer";
    }

    @GetMapping("/holidayCheck")
    public @ResponseBody Map<String, String> holidayCheck(){
        List<Schedule> schedules = scheduleRepository.findAll();
        Map<String, String> holidayMap = new HashMap<>();
        for (Schedule schedule : schedules){
            if (schedule.getStatus() != null || !ObjectUtils.isEmpty(schedule.getStatus())) {
                holidayMap.put(schedule.getScheduleDay(), schedule.getStatus().getName());
            }
        }
        return holidayMap;
    }
}
