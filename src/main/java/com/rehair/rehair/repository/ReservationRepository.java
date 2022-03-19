package com.rehair.rehair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rehair.rehair.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
