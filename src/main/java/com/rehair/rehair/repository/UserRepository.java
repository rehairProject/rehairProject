package com.rehair.rehair.repository;

import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
