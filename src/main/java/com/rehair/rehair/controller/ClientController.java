package com.rehair.rehair.controller;

import com.rehair.rehair.domain.Event;
import com.rehair.rehair.repository.EventRepository;
import com.rehair.rehair.service.EventService;
import com.rehair.rehair.validator.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventValidator eventValidator;


    @GetMapping("/about")
    public String about() {
        return "client/about";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "client/reservation";
    }

    @GetMapping("/notice")
    public String notice(Model model, @PageableDefault(size = 3, sort = "id",  direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        int startPage=Math.max(1,events.getPageable().getPageNumber()-4);
        int endPage = Math.min(events.getTotalPages(), events.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("events", events);
        return "client/notice";
    }

    @GetMapping("/reservation_check")
    public String reservationCheck() {
        return "client/reservation_check";
    }

    @GetMapping("/event_detail")
    public String eventDetail(Model model, @RequestParam(required = false) Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        model.addAttribute("event", event);
        return "client/event_detail";
    }

    @GetMapping("/event_writing")
    public String eventWriting(Model model, @RequestParam(required = false) Long id) {
        if(id==null){
            model.addAttribute("event", new Event());
        }else{
            Event event = eventRepository.findById(id).orElse(null);
            model.addAttribute("event", event);
        }

        return "client/event_writing";
    }

    @PostMapping("/event_writing")
    public String eventSubmit( Event event, BindingResult bindingResult, MultipartFile file) throws Exception {
        eventValidator.validate(event,bindingResult);
        if (bindingResult.hasErrors()) {
            return "client/event_writing";
        }
        eventService.save(event, file);
        return "redirect:/client/notice";
    }

    @GetMapping("/membership")
    public String membership() {
        return "client/membership";
    }
}
