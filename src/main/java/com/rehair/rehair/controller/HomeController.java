package com.rehair.rehair.controller;

import com.rehair.rehair.domain.*;
import com.rehair.rehair.repository.AuthRepository;
import com.rehair.rehair.repository.ReservationRepository;
import com.rehair.rehair.repository.ScheduleRepository;
import com.rehair.rehair.repository.UserRepository;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.*;

@Controller
@RequiredArgsConstructor
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
                        String searchKeyword,
                        @RequestParam(required = false) String reservationYear, @RequestParam(required = false) String reservationMonth, @RequestParam(required = false, value = "tabFlag") String tabFlag
    ){
        //유저 검색
        Page<User> users = null;
        if(searchKeyword==null){
            users = userRepository.findAll(pageable);
        }else{
            users = userRepository.findByUsernameContaining(searchKeyword,pageable);
        }

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
        model.addAttribute("tabFlag", tabFlag);
        if (tabFlag == null){
            model.addAttribute("tabFlag", "null");
        }
        return "admin";
    }
    
    @ResponseBody
    @GetMapping("/admin/chart")
    public List<Reservation> reservationJsonForChart(@RequestParam(required = false) String reservationYear, @RequestParam(required = false) String reservationMonth) {
    	String day = "";

    	LocalDate now = LocalDate.now();
		if(reservationYear == null && reservationMonth == null) {
			day = now.toString().substring(0, 7);;
		}else {
			day = reservationYear + "-" + reservationMonth;
		}
		List<Reservation> reservations = reservationRepository.findByDayContaining(day);
    	return reservations;
    }

    //멤버십 등급&권한 변경
    @GetMapping("/admin/gradeAuthModify")
    public String admin(Grade grade, String selectedUser,String auth, String page, Model model){
        User user = userRepository.findByUsername(selectedUser);
        if(grade!=null){
            user.setGrade(grade);
        }

        Auth saveAuth = new Auth();
        if(auth!=null && auth.equals("ROLE_ADMIN")) {
            saveAuth.setId(1L);
            user.getAuths().set(0, saveAuth);
        } else if(auth!=null && auth.equals("ROLE_DESIGNER")) {
            saveAuth.setId(2L);
            user.getAuths().set(0, saveAuth);
        } else if(auth!=null && auth.equals("ROLE_USER")) {
            saveAuth.setId(3L);
            user.getAuths().set(0, saveAuth);
        }
        userRepository.save(user);
        model.addAttribute("tabFlag","manage");
        return "redirect:/admin?page=" + page;
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
        Schedule schedule = scheduleRepository.getById(date);
        schedule.setStatus(null);
        scheduleRepository.save(schedule);
        return "redirect:/admin?tabFlag=del";
    }

    /* 디자이너 */
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
