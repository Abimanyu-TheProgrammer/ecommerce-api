package com.example.shopAPI.auth.controller;

import com.example.shopAPI.auth.dao.JwtAuthenticationResponse;
import com.example.shopAPI.auth.dao.ResetPasswordRequest;
import com.example.shopAPI.auth.dao.SignInRequest;
import com.example.shopAPI.auth.dao.SignUpRequest;
import com.example.shopAPI.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/reset")
    public ResponseEntity<JwtAuthenticationResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return new ResponseEntity<>(authenticationService.resetPassword(
                resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword()), HttpStatus.OK);
    }
}
