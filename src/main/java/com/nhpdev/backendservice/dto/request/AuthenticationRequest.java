package com.nhpdev.backendservice.dto.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String email,
        String password
) {
}
