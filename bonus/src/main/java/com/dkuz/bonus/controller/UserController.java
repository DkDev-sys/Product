package com.dkuz.bonus.controller;

import com.dkuz.bonus.dto.UserDto;
import com.dkuz.bonus.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> create(@RequestBody UserDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
}
