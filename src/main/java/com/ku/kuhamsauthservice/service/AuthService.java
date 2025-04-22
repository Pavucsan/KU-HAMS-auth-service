package com.ku.kuhamsauthservice.service;

import com.ku.kuhamsauthservice.dto.user.request.AuthRequest;
import com.ku.kuhamsauthservice.dto.user.response.AuthResponse;

public interface AuthService {
    public AuthResponse login(AuthRequest request);
    public void register(AuthRequest request);
}
