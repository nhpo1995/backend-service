package com.nhpdev.backendservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.access-key}")
    private String accessToken;

    @Value("${jwt.refresh-key}")
    private String refreshToken;

    public String generateAccessToken(String userId) {
        //create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        //body
        Date expirationTime = Date.from(new Date().toInstant().plus(30, ChronoUnit.MINUTES));
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issuer("http://localhost:8080")
                .expirationTime(expirationTime)
                .claim("role", "GUEST")
                .build();
        //sign
        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(accessToken));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(String userId) {
        //create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        //body
        Date expirationTime = Date.from(new Date().toInstant().plus(14, ChronoUnit.DAYS));
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issuer("http://localhost:8080")
                .expirationTime(expirationTime)
                .build();
        //sign
        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(refreshToken));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public SignedJWT validateToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean isValid = signedJWT.verify(new MACVerifier(refreshToken));
        if (!isValid)
            throw new RuntimeException("Invalid JWT");
        Date expiredDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(expiredDate.before(new Date()))
            throw new RuntimeException("JWT is expired");
        return signedJWT;
    }
}
