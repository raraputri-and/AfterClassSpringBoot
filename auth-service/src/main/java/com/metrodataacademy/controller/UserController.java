package com.metrodataacademy.controller;

import com.metrodataacademy.domain.dto.request.ReqUpdateUserDto;
import com.metrodataacademy.domain.dto.response.ResTemplateDto;
import com.metrodataacademy.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/update-profile")
    public ResponseEntity<ResTemplateDto> updateUser(@Valid @RequestBody ReqUpdateUserDto reqUpdateUserDto, @RequestHeader("Authorization") String authToken) {
        return userService.updateUser(reqUpdateUserDto, authToken);
    }

    @GetMapping("/get-profile")
    public ResponseEntity<ResTemplateDto> getProfile(@RequestHeader("Authorization") String authToken) {
        return userService.getProfile(authToken);
    }
}