package com.rehair.rehair.service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.ReservationStatus;
import com.rehair.rehair.repository.ReservationRepository;


@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	
	@Transactional
    @Scheduled(cron = "0 0 23 * * *") // 매일 23시에 실행
    public void reservationStatusAutomatic() {
        List<Reservation> reservations = reservationRepository.findAll();
        LocalDate now = LocalDate.now();

        List<Reservation> cancelReservations = reservations.stream().filter(
        		r -> r.getDay().equals(now.toString()) && 
        		r.getStatus().equals(ReservationStatus.RESERVATION)
        		).collect(Collectors.toList());
        
        for (Reservation reservation : cancelReservations) {
        	
			reservation.setStatus(ReservationStatus.CANCEL);
		}
    }
}
