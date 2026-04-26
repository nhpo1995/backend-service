package com.nhpdev.backendservice.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.access-key}")
    private String accessToken;

    private NimbusJwtDecoder nimbusJwtDecoder;

    @PostConstruct
    public void init() {
        if(Objects.isNull(nimbusJwtDecoder)) {
            SecretKey key = new SecretKeySpec(accessToken.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(key)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return nimbusJwtDecoder.decode(token);
        } catch (Exception e) {
            // Log lỗi nếu cần
            throw new JwtException(e.getMessage());
        }
    }
}
