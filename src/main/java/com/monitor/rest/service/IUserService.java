package com.monitor.rest.service;

import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.dto.user.UserResponse;
import com.monitor.rest.model.User;

public interface IUserService {
    
    UserResponse createUser(UserRequest user);

    UserResponse getUserById(Long userId);

    UserResponse getUserByEmail(String userEmail);

    UserResponse updateUser(Long userId, UserRequest user);

    UserResponse deleteUserById(Long userId);

    User getUserWithPlants(Long userId);
}
