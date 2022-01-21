package com.rehair.rehair.service;

import com.rehair.rehair.domain.Grade;
import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        String encode = passwordEncoder.encode(user.getPassword()); // 비밀번호 암호화
        user.setPassword(encode);

        user.setGrade(Grade.WELCOME); // 기본값 WELCOME
        user.setJoinDate(LocalDate.now());
        user.setEnabled(true); // 계정 활성화

        return userRepository.save(user);
    }
}
