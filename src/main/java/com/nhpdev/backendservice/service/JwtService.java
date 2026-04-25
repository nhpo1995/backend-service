package com.nhpdev.backendservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(String userId) {
        //create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        //body
        Date issueTime = new Date();
        Date expirationTime = Date.from(issueTime.toInstant().plusSeconds(1800));

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issuer("http://localhost:8080")
                .expirationTime(expirationTime)
                .claim("role", "GUEST")
                .build();

        //sign
        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secretKey));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }
}
