package com.rehair.rehair.controller;

import com.rehair.rehair.domain.*;
import com.rehair.rehair.dto.ScheduleDto;
import com.rehair.rehair.repository.*;
import com.rehair.rehair.service.EventService;

import com.rehair.rehair.service.ReservationService;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

	private final NoticeRepository noticeRepository;
	private final EventService eventService;
	private final EventRepository eventRepository;
	private final ScheduleRepository scheduleRepository;
	private final ReservationRepository reservationRepository;
	private final ReservationService reservationService;
	private final UserService userService;
	private final UserRepository userRepository;

	@GetMapping("/about")
	public String about() {
		return "client/about";
	}

	@GetMapping("/reservation")
	public String reservation(@RequestParam(required = false, value = "tabFlag") boolean tabFlag, Model model, Principal principal) {
		String currentUsername = principal.getName();
		User currentUserInfo = userService.currentUserInfo(currentUsername);

		List<Reservation> reservations = reservationRepository.findUseJPQL(currentUserInfo);
		model.addAttribute("reservations", reservations);
		model.addAttribute("recent", reservations.get(0));
		model.addAttribute("tabFlag", tabFlag);
		return "client/reservation";
	}

	@GetMapping("/reservation/{selectedDate}")
	public ResponseEntity<Schedule> getSchedule(@PathVariable String selectedDate){
		ScheduleDto scheduleDto = new ScheduleDto(scheduleRepository.getById(selectedDate));
		System.out.println("scheduleDto.getStatus() = " + scheduleDto.getStatus());
		Schedule schedule = scheduleDto.toSchedule();
		return ResponseEntity.ok().body(schedule);
	}

	@PostMapping("/reservation_check")
	public String reservationCheck(@RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("designer") String designer, @RequestParam("style") String style, Model model, @RequestParam("price") String price) {
		model.addAttribute("date", date);
		model.addAttribute("time", time);
		model.addAttribute("designer", designer);
		model.addAttribute("style", style);
		model.addAttribute("price", price);
		return "client/reservation_check";
	}

	@PostMapping("/reservation")
	public String reservationSave(Model model, Principal principal, @RequestParam String date, @RequestParam String time, @RequestParam String designer, @RequestParam String style, @RequestParam String price, RedirectAttributes redirectAttributes) {
		//현재 로그인된 유저정보
		String currentUser = principal.getName();
		User currentUserInfo = userService.currentUserInfo(currentUser);

		Reservation reservation = new Reservation();
		Schedule schedule = new Schedule();
		schedule.setScheduleDay(date);
		reservation.setDay(date);
		reservation.setSchedule(schedule);   //schedule date fk 값 세팅
		reservation.setTime(time);
		reservation.setDesigner(designer);
		reservation.setStyle(style);
		reservation.setStatus(ReservationStatus.RESERVATION);
		int intPrice = Integer.parseInt(price.replace(",",""));
		reservation.setPrice(intPrice);
		reservation.setUser(currentUserInfo);
		reservationRepository.save(reservation);

		redirectAttributes.addAttribute("reservationStatus", true); // 상태 전송
		return "redirect:/client/reservation" + "?tabFlag=" + true;
	}

	@GetMapping("reservationCancel")
	public String reservationCancel(@RequestParam(required = false) Long id){
		Reservation recent = reservationRepository.findById(id).orElse(null);
		recent.setStatus(ReservationStatus.CANCEL);
		reservationRepository.save(recent);
		return "redirect:/client/reservation";
	}

	// == Notice 로직 시작 ==//

	@GetMapping("/notice")
	public String notices(Model model,
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Notice> notices = noticeRepository.findAll(pageable);
		int startPage = Math.max(1, notices.getPageable().getPageNumber() - 4);
		int endPage = Math.min(notices.getTotalPages(), notices.getPageable().getPageNumber() + 4);

		model.addAttribute("indexCalculator", notices.getTotalElements() - notices.getPageable().getPageNumber() * 10);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("notices", notices);
		return "client/notice";
	}

	@GetMapping("/notice_detail")
    public String notice(Model model, @RequestParam(required = false) Long id) {
    	Notice notice = noticeRepository.findById(id).orElse(null);
		model.addAttribute("notice", notice);
    	return "client/notice_detail";
    }
	
	@GetMapping("/notice_delete") 
	public String noticeDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
	    noticeRepository.deleteById(id);
		redirectAttributes.addAttribute("deleteStatus", true); // 상태 전송
		return "redirect:/client/notice";
	}

	@GetMapping("/notice_writing") // 입력, 수정 폼 공유
	public String noticeWriting(Model model, @RequestParam(required = false) Long id) {
		if (id == null)
			model.addAttribute("notice", new Notice());
		else {
			Notice notice = noticeRepository.findById(id).orElse(null);
			model.addAttribute("notice", notice);
		}
		return "client/notice_writing";
	}

	@PostMapping("/notice_writing")
	public String noticeSubmit(@ModelAttribute Notice notice, RedirectAttributes redirectAttributes) {
		noticeRepository.save(notice);

		redirectAttributes.addAttribute("writeStatus", true); // 상태 전송
		return "redirect:/client/notice";
	}

	// == Notice 로직 끝 ==//

	// == Event 로직 시작 ==//

	@GetMapping("/event")
	public String event(Model model,
						@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Event> events = eventRepository.findAll(pageable);
		int startPage = Math.max(1, events.getPageable().getPageNumber() - 4);
		int endPage = Math.min(events.getTotalPages(), events.getPageable().getPageNumber() + 4);

		model.addAttribute("indexCalculator", events.getTotalElements() - events.getPageable().getPageNumber() * 3);

		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("events", events);
		return "client/event";
	}

	@GetMapping("/event_detail")
	public String eventDetail(Model model, @RequestParam(required = false) Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		model.addAttribute("event", event);
		return "client/event_detail";
	}

	@GetMapping("/event_writing")
	public String eventWriting(Model model) {
		model.addAttribute("event", new Event());
		return "client/event_writing";
	}

	@PostMapping("/event_writing")
	public String eventSubmit(@ModelAttribute Event event, MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		Event saveEvent = eventService.upload(file);
		event.setServerFileName(saveEvent.getServerFileName());
		event.setUploadFileName(saveEvent.getUploadFileName());
		eventRepository.save(event);

		redirectAttributes.addAttribute("writeStatus", true);
		return "redirect:/client/event";
	}

	@GetMapping("/event_modifying")
	public String eventModifyingForm(Model model, @RequestParam(required = false) Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		model.addAttribute("event", event);
		return "client/event_modifying";
	}
	@PostMapping("/event_modifying")
	public String eventModifying(@ModelAttribute Event event, MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {

		//파일을 수정하지 않았을 경우
		//이미 저장돼있는 파일 가져와서 저장
		if(file.isEmpty()) {
			Optional<Event> savedEvent = eventRepository.findById(event.getId());
			event.setServerFileName(savedEvent.get().getServerFileName());
			event.setUploadFileName(savedEvent.get().getUploadFileName());
		}else{
			Event saveEvent = eventService.upload(file);
			event.setServerFileName(saveEvent.getServerFileName());
			event.setUploadFileName(saveEvent.getUploadFileName());
		}
		eventRepository.save(event);

		redirectAttributes.addAttribute("modifyStatus", true);
		return "redirect:/client/event";
	}

	@GetMapping("/event_delete")
	public String eventDelete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		eventRepository.deleteById(id);
		redirectAttributes.addAttribute("deleteStatus", true); // 상태 전송
		return "redirect:/client/event";
	}

	// == Event 로직 끝 ==//


}
