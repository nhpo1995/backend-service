package com.nhpdev.backendservice.dto.response;

import lombok.Builder;

@Builder
public record UserCreateResponse(
        String id,
        String username,
        String email
) {
}
