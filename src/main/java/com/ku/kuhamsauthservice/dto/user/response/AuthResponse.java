package com.ku.kuhamsauthservice.dto.user.response;

public record AuthResponse(Long userId, String username, String token, Long patientId) {}