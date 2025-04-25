package com.ku.kuhamsauthservice.dto.user;

public record DoctorCreateRequest(
        Long userId,
        String username,
        String specialization,
        String phoneNumber,
        String email,
        String availabilityStart,
        String availabilityEnd
) {}

