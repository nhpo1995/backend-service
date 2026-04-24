package com.nhpdev.backendservice.dto.request;

import lombok.Builder;

@Builder
public record UserCreateRequest(
        String email,
        String username,
        String password
) {
}
