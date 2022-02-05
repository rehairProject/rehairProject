package com.rehair.rehair.controller;

import com.rehair.rehair.domain.User;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    @Autowired
    private final UserService userService;

    @PostMapping("/account/duplicateUsername")
    public String duplicateUsername(@RequestBody String username) {
        User findUser = userService.duplicateUsername(username);
        String result = null;
        if (ObjectUtils.isEmpty(findUser)){
            return "notExist";
        } else {
            return  "exist";
        }
    }
}
