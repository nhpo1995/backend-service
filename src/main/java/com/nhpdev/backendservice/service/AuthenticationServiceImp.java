package com.nhpdev.backendservice.service;

import com.nhpdev.backendservice.dto.request.AuthenticationRequest;
import com.nhpdev.backendservice.dto.response.AuthenticationResponse;
import com.nhpdev.backendservice.entity.User;
import com.nhpdev.backendservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //get user by email
        User user = userRepository.getUserByEmail(request.email());
        if (user == null)
            throw new RuntimeException("User's email is not exist");
        var rawPassword = request.password();
        var hashedPassword = user.getPassword();
        //Compare 2 password
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(rawPassword, hashedPassword))
            throw new RuntimeException("Password does not match");
        return AuthenticationResponse.builder()
                .token("Token")
                .build();
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
