package com.qr_audio.backend.controllers;

import com.qr_audio.backend.dto.ApiResponse;
import com.qr_audio.backend.dto.LoginRequest;
import com.qr_audio.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        if ("admin".equals(loginRequest.getUsername()) && "admin123".equals(loginRequest.getPassword())) {
            
            String token = tokenProvider.generateToken(loginRequest.getUsername());
            
            return ResponseEntity.ok(new ApiResponse<>(200, "Đăng nhập thành công", token));
        }
        
        return ResponseEntity.status(401).body(
            new ApiResponse<>(401, "Sai tài khoản hoặc mật khẩu", null)
        );
    }
}