package com.monitor.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.dto.user.UserResponse;
import com.monitor.rest.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = userService.getUserById(userId);

        if (user.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{userEmail}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userEmail) {
        UserResponse user = userService.getUserByEmail(userEmail);

        if (user.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long userId, @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(userId, request);

        if (user.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/del/{userId}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long userId) {
        UserResponse user = userService.deleteUserById(userId);

        if (user.getUserId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK); 
    }

    @GetMapping("/{userId}/plants")
    public ResponseEntity<List<PlantResponse>> getUserWithPlants(@PathVariable Long userId) {
        List<PlantResponse> plants = userService.getUserWithPlants(userId);

        if (plants == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plants, HttpStatus.OK);     
    }
    
}
