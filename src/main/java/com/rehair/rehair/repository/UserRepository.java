package com.rehair.rehair.repository;

import com.rehair.rehair.domain.Reservation;
import com.rehair.rehair.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    //아이디 검색
    Page<User> findByUsernameContaining(String searchKeyword, Pageable pageable);

}
