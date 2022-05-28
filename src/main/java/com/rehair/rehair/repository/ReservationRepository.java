package com.rehair.rehair.repository;

import com.rehair.rehair.domain.ReservationStatus;
import com.rehair.rehair.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rehair.rehair.domain.Reservation;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findByUser(User currentUser);
    Page<Reservation> findByDesigner( String name, Pageable pageable);
    List<Reservation> findByDayAndDesignerAndStatus(String nowDate, String name, ReservationStatus status);

}

