package com.nhpdev.backendservice.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
