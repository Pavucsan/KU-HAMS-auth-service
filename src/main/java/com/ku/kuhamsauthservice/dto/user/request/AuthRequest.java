package com.ku.kuhamsauthservice.dto.user.request;

// in auth-service
public record AuthRequest(
        String username,
        String password,
        String role,            // “DOCTOR” or “PATIENT”
        String specialization,   // only for DOCTOR
        String phoneNumber,      // for both
        String email,            // for both
        String availabilityStart,// only for DOCTOR
        String availabilityEnd,  // only for DOCTOR
        String dateOfBirth,      // only for PATIENT
        String gender            // only for PATIENT
) {}
