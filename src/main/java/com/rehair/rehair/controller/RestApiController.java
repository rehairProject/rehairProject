package com.rehair.rehair.controller;

import com.rehair.rehair.domain.User;
import com.rehair.rehair.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

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
