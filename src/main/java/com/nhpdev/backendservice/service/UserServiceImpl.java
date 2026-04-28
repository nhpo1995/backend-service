package com.nhpdev.backendservice.service;

import com.nhpdev.backendservice.dto.request.ChangePasswordRequest;
import com.nhpdev.backendservice.dto.request.UserCreateRequest;
import com.nhpdev.backendservice.dto.request.UserUpdateRequest;
import com.nhpdev.backendservice.dto.response.ChangePasswordResponse;
import com.nhpdev.backendservice.dto.response.UserCreateResponse;
import com.nhpdev.backendservice.dto.response.UserDetailResponse;
import com.nhpdev.backendservice.dto.response.UserUpdateResponse;
import com.nhpdev.backendservice.entity.User;
import com.nhpdev.backendservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    private UserDetailResponse mapToResponse(User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .build();
    }

    @Override
    public List<UserDetailResponse> getAllUser() {
        var users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "email"));
        return users.stream()
                .map(this::mapToResponse).toList();
    }

    @Override
    public UserDetailResponse getUserById(String id) {
        return userRepository.findById(id).map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public UserCreateResponse createUser(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.email()))
            throw new RuntimeException("User's email already exist");
        if(userRepository.existsByUsername(request.username()))
            throw new RuntimeException("User's username already exist");
        var user = User.builder()
                        .email(request.email())
                        .username(request.username())
                        .password(authenticationService.hashPassword(request.password()))
                .build();
        userRepository.save(user);
        return UserCreateResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail()).build();
    }

    @Override
    public UserUpdateResponse updateUser(String id, UserUpdateRequest request) {
        if(!userRepository.existsById(id))
            throw new RuntimeException("User does not exist");
        User user = userRepository.findById(id).get();
        user.setEmail(request.email());
        user.setUsername(request.username());
        userRepository.save(user);
        return UserUpdateResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public ChangePasswordResponse changePassword(String id, ChangePasswordRequest request) {
        if(!userRepository.existsById(id))
            throw new RuntimeException("User does not exist");
        User user = userRepository.findById(id).get();
        if(!authenticationService.verifyPassword(request.oldPassword(), user.getPassword()))
            throw new RuntimeException("Old Password does not match");
        user.setPassword(authenticationService.hashPassword(request.newPassword()));
        userRepository.save(user);
        return ChangePasswordResponse.builder()
                .newPassword(request.newPassword())
                .build();
    }


    @Override
    public void deleteUser(String id) {
        if(!userRepository.existsById(id))
            throw new RuntimeException("User does not exist");
        userRepository.deleteById(id);
    }
}
