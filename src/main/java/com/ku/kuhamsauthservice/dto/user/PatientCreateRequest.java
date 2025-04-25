package com.ku.kuhamsauthservice.dto.user;

public record PatientCreateRequest(
        Long userId,
        String username,
        String dateOfBirth,
        String gender,
        String phoneNumber,
        String email
) {}