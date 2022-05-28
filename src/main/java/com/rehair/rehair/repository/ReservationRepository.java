package com.rehair.rehair.repository;

import com.rehair.rehair.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rehair.rehair.domain.Reservation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findByUser(User currentUser);

    List<Reservation> findByDayContaining(String day);
}

