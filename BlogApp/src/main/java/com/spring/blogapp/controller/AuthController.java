package com.spring.blogapp.controller;

import com.spring.blogapp.model.LoginDTO;
import com.spring.blogapp.model.RegisterDTO;
import com.spring.blogapp.response.JWTAuthResponse;
import com.spring.blogapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping({"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO account){
        String token = authService.login(account);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping({"/register", "/signup"})
    public ResponseEntity<String> login(@RequestBody RegisterDTO account){
        return new ResponseEntity(authService.register(account), HttpStatus.CREATED);
    }
}
