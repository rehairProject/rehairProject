package com.rehair.rehair.service;

import com.rehair.rehair.domain.Auth;
import com.rehair.rehair.domain.User;
import com.rehair.rehair.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Auth auth = new Auth();
        auth.setId(1l);
        user.getAuth().add(auth);
        return userRepository.save(user);

    }
}
