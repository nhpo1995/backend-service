package com.nhpdev.backendservice.service;

import com.nhpdev.backendservice.dto.request.AccessTokenRequest;
import com.nhpdev.backendservice.dto.request.AuthenticationRequest;
import com.nhpdev.backendservice.dto.response.AuthenticationResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String hashPassword(String rawPassword);
    boolean verifyPassword(String rawPassword, String hashedPassword);
    AuthenticationResponse getAccessToken(AccessTokenRequest request) throws ParseException;
}
