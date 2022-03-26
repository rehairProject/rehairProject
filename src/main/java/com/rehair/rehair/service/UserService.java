package com.rehair.rehair.service;

import com.rehair.rehair.domain.*;
import com.rehair.rehair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User joinUser(User user) {
        String encode = passwordEncoder.encode(user.getPassword()); // 비밀번호 암호화
        user.setPassword(encode);

        user.setGrade(Grade.WELCOME); // 기본값 WELCOME
        user.setEnabled(true);        // 기본값 true(1)
        user.setJoinDate(LocalDate.now());

        Auth auth = new Auth();
        auth.setId(3L);
        user.getAuths().add(auth);    // 1L=AUTH_ADMIN, 2L=AUTH_DESIGNER, 3L=AUTH_USER
        return userRepository.save(user);
    }

    public User duplicateUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User currentUserInfo(String currentUser){
        User user = userRepository.findByUsername(currentUser);
        return user ;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 3 * *") // 매달 3일 0시에 실행
    public void memberShipAutomatic() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            int total = 0;

            List<Reservation> reservations = user.getReservations().stream()
                    .filter(r -> r.getStatus().equals(ReservationStatus.PAYMENT_COMPLETED)
                            && r.getDateCreated().isAfter(LocalDateTime.now().minusYears(1)))
                    .collect(Collectors.toList());
            for (Reservation reservation : reservations) {
                total += reservation.getPrice();
            }

            if (total >= 300000) {
                user.setGrade(Grade.VIP);
                userRepository.save(user);
            } else if (100000 < total && total < 300000){
                user.setGrade(Grade.FAMILY);
                userRepository.save(user);
            }
        }
    }
}
