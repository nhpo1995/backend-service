package com.nhpdev.backendservice.controller;

import com.nhpdev.backendservice.dto.request.AuthenticationRequest;
import com.nhpdev.backendservice.dto.response.ApiResponse;
import com.nhpdev.backendservice.dto.response.AuthenticationResponse;
import com.nhpdev.backendservice.service.AuthenticationService;
import com.nhpdev.backendservice.service.AuthenticationServiceImp;
import com.nhpdev.backendservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Api")
public class AuthenticationController {
    private final AuthenticationServiceImp authenticationServiceImp;

    @Tag(name = "LOGIN")
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .success(true)
                .data(authenticationServiceImp.authenticate(request))
                .code(HttpStatus.OK.value())
                .build();
    }
}
