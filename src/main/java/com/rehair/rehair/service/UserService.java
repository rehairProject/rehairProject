package com.rehair.rehair.service;

import com.rehair.rehair.domain.Auth;
import com.rehair.rehair.domain.Grade;
import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
