package com.nhpdev.backendservice.service;

import com.nhpdev.backendservice.dto.request.ChangePasswordRequest;
import com.nhpdev.backendservice.dto.request.UserCreateRequest;
import com.nhpdev.backendservice.dto.request.UserUpdateRequest;
import com.nhpdev.backendservice.dto.response.ChangePasswordResponse;
import com.nhpdev.backendservice.dto.response.UserCreateResponse;
import com.nhpdev.backendservice.dto.response.UserDetailResponse;
import com.nhpdev.backendservice.dto.response.UserUpdateResponse;

import java.util.List;

public interface UserService {
    //GET
    List<UserDetailResponse>  getAllUser();
    UserDetailResponse getUserById(String id);
    //POST
    UserCreateResponse createUser(UserCreateRequest request);
    //PATCH
    UserUpdateResponse updateUser(String id, UserUpdateRequest request);
    ChangePasswordResponse changePassword(String id, ChangePasswordRequest request);
    //DELETE
    void deleteUser(String id);

}
