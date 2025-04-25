package com.ku.kuhamsauthservice.service.impl;


import com.ku.kuhamsauthservice.config.security.JwtConfig;
import com.ku.kuhamsauthservice.controller.AppointmentClient;
import com.ku.kuhamsauthservice.dto.user.DoctorCreateRequest;
import com.ku.kuhamsauthservice.dto.user.PatientCreateRequest;
import com.ku.kuhamsauthservice.dto.user.request.AuthRequest;
import com.ku.kuhamsauthservice.dto.user.response.AuthResponse;
import com.ku.kuhamsauthservice.entity.TokenStore;
import com.ku.kuhamsauthservice.entity.User;
import com.ku.kuhamsauthservice.repository.TokenStoreRepository;
import com.ku.kuhamsauthservice.repository.UserRepository;
import com.ku.kuhamsauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenStoreRepository tokenStoreRepository;
    private final JwtConfig jwtConfig;
    private final AppointmentClient appointmentClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtConfig.generateToken(user.getUsername());
        Date expiryDate = jwtConfig.extractExpiration(token);

        tokenStoreRepository.save(TokenStore.builder()
                .token(token)
                .issuedAt(LocalDateTime.now())
                .expiresAt(expiryDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .user(user)
                .build());

        return new AuthResponse(user.getId(), user.getUsername(), token);
    }



    @Override
    public void register(AuthRequest request) {
        /*if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();

        userRepository.save(user);*/

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        User saved = userRepository.save(new User(null, request.username(), passwordEncoder.encode(request.password()), request.role()));


        if (saved.getRole().equals("DOCTOR")) {
            appointmentClient.createDoctor(new DoctorCreateRequest(
                    saved.getId(),
                    saved.getUsername(),
                    request.specialization(),
                    request.phoneNumber(),
                    request.email(),
                    request.availabilityStart(),
                    request.availabilityEnd()
            ));
        } else {
            appointmentClient.createPatient(new PatientCreateRequest(
                    saved.getId(),
                    saved.getUsername(),
                    request.dateOfBirth(),
                    request.gender(),
                    request.phoneNumber(),
                    request.email()
            ));
        }
    }
}

