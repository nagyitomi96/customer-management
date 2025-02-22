package com.customerapp.management.controller;

import com.customerapp.management.dto.AuthRequestDTO;
import com.customerapp.management.security.JwtUtil;
import com.customerapp.management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request)
    {
        if (userService.findByUsername(request.getUsername()).isPresent())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        userService.createUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }
}
