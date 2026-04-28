package com.nhpdev.backendservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JwtService {
    public String generateAccessToken(String userId);
    public String generateRefreshToken(String userId);
    public SignedJWT validateToken(String token) throws ParseException, JOSEException;
}
