package com.rehair.rehair.service;

import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.ReservationStatus;
import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.ReservationRepository;
import com.rehair.rehair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;

//    public List<Reservation> findUseJPQL(Principal principal){
//        String currentUser = principal.getName();
//        User currentUserInfo = userService.currentUserInfo(currentUser);
//        return this.reservationRepository.findUseJPQL(currentUserInfo);
//    }
}
