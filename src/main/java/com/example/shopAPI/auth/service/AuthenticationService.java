package com.example.shopAPI.auth.service;

import com.example.shopAPI.auth.dao.JwtAuthenticationResponse;
import com.example.shopAPI.auth.dao.SignInRequest;
import com.example.shopAPI.auth.dao.SignUpRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
