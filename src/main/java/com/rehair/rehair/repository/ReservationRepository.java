package com.rehair.rehair.repository;

import com.rehair.rehair.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rehair.rehair.domain.Reservation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
//    Reservation findByUser(User user);

//    @Query("SELECT * FROM Reservation reservation where reservation.fk_user_id=:user order by reservation.reservation_day desc")
//    List<Reservation> findUseJPQL(@Param("user") User user);
}

