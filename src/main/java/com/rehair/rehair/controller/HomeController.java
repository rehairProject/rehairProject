package com.rehair.rehair.controller;

import com.rehair.rehair.domain.*;
import com.rehair.rehair.repository.AuthRepository;
import com.rehair.rehair.repository.ReservationRepository;
import com.rehair.rehair.repository.ScheduleRepository;
import com.rehair.rehair.repository.UserRepository;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final AuthRepository authRepository;
    private final UserService userService;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model,
                        @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        String searchKeyword
    ){
        //유저 검색
        Page<User> users = null;
        if(searchKeyword==null){
            users = userRepository.findAll(pageable);
        }else{
            users = userRepository.findByUsernameContaining(searchKeyword,pageable);
        }

        List<Schedule> schedules = scheduleRepository.findAll();

        List grades = List.of(Grade.values());
        List<Auth> auths= authRepository.findAll();

        int startPage = Math.max(1, users.getPageable().getPageNumber() - 4);
        int endPage = Math.min(users.getTotalPages(), users.getPageable().getPageNumber() + 4);

        model.addAttribute("schedules", schedules);
        model.addAttribute("grades", grades);
        model.addAttribute("auths", auths);
        model.addAttribute("indexCalculator", users.getTotalElements() - users.getPageable().getPageNumber() * 3);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("users", users);
        return "admin";
    }

    //멤버십 등급&권한 변경
    @GetMapping("/admin/gradeAuthModify")
    public String admin(Grade grade, String selectedUser,String auth){
        //멤버십등급 변경
        System.out.println(grade);
        System.out.println(selectedUser);
        System.out.println(auth); //ROLE_ADMIN

        User user = userRepository.findByUsername(selectedUser);

        if(grade!=null){
            user.setGrade(grade);
        }else if(auth!=null){
            System.out.println(auth);

            user.getAuths().get(0).setId(2l);
            System.out.println("여기");
            System.out.println(user.getAuths().get(0).getId());
            System.out.println(user.getAuths().get(0).getName());
            System.out.println(user.getName());
        }

        userRepository.save(user);

        return "redirect:/admin";
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

    @GetMapping("/designer")
    public String designerPage(Principal principal, Model model,@PageableDefault(size = 10, sort = "day", direction = Sort.Direction.DESC) Pageable pageable){
        String currentUsername = principal.getName();
        User currentUserInfo = userService.currentUserInfo(currentUsername);
        String name = currentUserInfo.getName();

//        String nowDate = LocalDate.now().toString();
        String nowDate = "2022-05-16";

        //로그인된 디자이너 본인 전체 예약리스트
        Page<Reservation> allReservations = reservationRepository.findByDesigner(name, pageable);
        //로그인된 디자이너 본인 오늘 예약리스트
        ReservationStatus reservationStatus = ReservationStatus.RESERVATION;
        List<Reservation> todayReservations = reservationRepository.findByDayAndDesignerAndStatus(nowDate, name, reservationStatus);
        //로그인된 디자이너 본인 오늘 예약확정 리스트
        ReservationStatus completeStatus = ReservationStatus.PAYMENT_COMPLETED;
        List<Reservation> completeTodayReservation = reservationRepository.findByDayAndDesignerAndStatus(nowDate, name, completeStatus);


        int startPage = Math.max(1, allReservations.getPageable().getPageNumber() - 4);
        int endPage = Math.min(allReservations.getTotalPages(), allReservations.getPageable().getPageNumber() + 4);

        model.addAttribute("indexCalculator", allReservations.getTotalElements() - allReservations.getPageable().getPageNumber() * 10);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reservations", allReservations);
        model.addAttribute("todayReservations", todayReservations);
        model.addAttribute("completeTodayReservation", completeTodayReservation);
        return "designer";
    }
    @PostMapping("/designer/reservationComplete")
    public String reservationComplete(@RequestParam(required = false) Long todayId){
        System.out.println(todayId);
        Optional<Reservation> selectedReservation = reservationRepository.findById(todayId);
        selectedReservation.get().setStatus(ReservationStatus.PAYMENT_COMPLETED);
        reservationRepository.save(selectedReservation.get());
        return "redirect:/designer";
    }
}

