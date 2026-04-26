package com.nhpdev.backendservice.service;

import com.nhpdev.backendservice.dto.request.AccessTokenRequest;
import com.nhpdev.backendservice.dto.request.AuthenticationRequest;
import com.nhpdev.backendservice.dto.response.AuthenticationResponse;
import com.nhpdev.backendservice.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //get user by email
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user = (User) authentication.getPrincipal();
        assert user != null;
        var accessToken = jwtService.generateAccessToken(user.getId());
        var refreshToken = jwtService.generateRefreshToken(user.getId());
        return AuthenticationResponse.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse getAccessToken(AccessTokenRequest request) {
        var token = request.refreshToken();
        if (token == null)
            throw new RuntimeException("Token is null");
        try {
            SignedJWT signedJWT = jwtService.validateToken(token);
            String userId = signedJWT.getJWTClaimsSet().getSubject();
            String accessToken = jwtService.generateAccessToken(userId);
            return AuthenticationResponse.builder()
                    .userId(userId)
                    .accessToken(accessToken)
                    .refreshToken(token)
                    .build();
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
