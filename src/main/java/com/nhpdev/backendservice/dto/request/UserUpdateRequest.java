package com.nhpdev.backendservice.dto.request;

public record UserUpdateRequest(
        String email,
        String username
) {
}
