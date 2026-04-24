package com.nhpdev.backendservice.controller;

import com.nhpdev.backendservice.dto.request.UserCreateRequest;
import com.nhpdev.backendservice.dto.request.UserUpdateRequest;
import com.nhpdev.backendservice.dto.response.ApiResponse;
import com.nhpdev.backendservice.dto.response.UserCreateResponse;
import com.nhpdev.backendservice.dto.response.UserDetailResponse;
import com.nhpdev.backendservice.dto.response.UserUpdateResponse;
import com.nhpdev.backendservice.service.UserServiceImp;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User APi", description = "All user CRUD apis")
public class UserController {
    private final UserServiceImp userService;
    //GET
    @Tag(name = "GET ALL")
    @GetMapping
    public ApiResponse<List<UserDetailResponse>> getALLUser() {
        return ApiResponse.<List<UserDetailResponse>>builder()
                .success(true)
                .data(userService.getAllUser())
                .code(HttpStatus.OK.value())
                .message("Create Success")
                .build();
    }
    @Tag(name = "GET ONE")
    @GetMapping("/{id}")
    public ApiResponse<UserDetailResponse> getUserById(@PathVariable String id) {
        return ApiResponse.<UserDetailResponse>builder()
                .success(true)
                .data(userService.getUserById(id))
                .code(HttpStatus.OK.value())
                .build();
    }
    //POST
    @Tag(name = "CREATE ONE")
    @PostMapping
    public ApiResponse<UserCreateResponse> createUser(@RequestBody UserCreateRequest request) {
        return ApiResponse.<UserCreateResponse>builder()
                .success(true)
                .data(userService.createUser(request))
                .code(HttpStatus.CREATED.value())
                .build();
    }
    //PATCH
    @Tag(name = "PATCH UPDATE")
    @PatchMapping("/{id}")
    public ApiResponse<UserUpdateResponse> updateUser(@PathVariable String id,
                                                      @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserUpdateResponse>builder()
                .success(true)
                .data(userService.updateUser(id, request))
                .code(HttpStatus.ACCEPTED.value())
                .build();
    }
    //DELETE
    @Tag(name = "DELETE ONE")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }
}
