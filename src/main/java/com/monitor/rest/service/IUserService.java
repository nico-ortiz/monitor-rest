package com.monitor.rest.service;

import java.util.List;

import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.user.TotalDataResponse;
import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.dto.user.UserResponse;

public interface IUserService {
    
    UserResponse createUser(UserRequest user);

    UserResponse getUserById(Long userId);

    UserResponse getUserByEmail(String userEmail);

    UserResponse updateUser(Long userId, UserRequest user);

    UserResponse deleteUserById(Long userId);

    List<PlantResponse> getUserWithPlants(Long userId);

    TotalDataResponse getTotals(Long userId);
}
