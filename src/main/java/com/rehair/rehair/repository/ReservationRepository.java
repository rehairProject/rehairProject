package com.rehair.rehair.repository;

import com.rehair.rehair.domain.ReservationStatus;
import com.rehair.rehair.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rehair.rehair.domain.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findByUserAndStatusNotLikeOrderByDayDesc(User currentUser,ReservationStatus reservation);
    List<Reservation> findByUserAndStatusOrderByDayDesc(User currentUser,ReservationStatus reservation);
    Page<Reservation> findByDesigner( String name, Pageable pageable);
    List<Reservation> findByDayAndDesignerAndStatus(String nowDate, String name, ReservationStatus status);
    List<Reservation> findByDayContaining(String day);
}

