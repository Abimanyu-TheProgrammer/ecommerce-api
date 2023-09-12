package com.example.shopAPI.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
}
