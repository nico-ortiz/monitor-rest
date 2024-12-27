package com.monitor.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.rest.dto.auth.AuthLoginRequest;
import com.monitor.rest.dto.auth.AuthResponse;
import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.service.impl.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    
    // It returns 409 if email is registered
    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserRequest request) {
        AuthResponse authResponse = userDetailsService.createUser(request);

        if (authResponse.getToken().equals("") && !authResponse.isStatus()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(userDetailsService.createUser(request), HttpStatus.CREATED);
    }

    // It returns 401 if credentials are invalid
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request) {
        AuthResponse authResponse = userDetailsService.login(request);
        
        if (authResponse.getToken().equals("") && !authResponse.isStatus()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userDetailsService.login(request), HttpStatus.OK);
    }   
}
