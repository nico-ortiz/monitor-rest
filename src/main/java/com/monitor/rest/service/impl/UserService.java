package com.monitor.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.user.TotalDataResponse;
import com.monitor.rest.dto.user.UserRequest;
import com.monitor.rest.dto.user.UserResponse;
import com.monitor.rest.dto.user.UserWithPlants;
import com.monitor.rest.mapper.UserMapper;
import com.monitor.rest.model.User;
import com.monitor.rest.repository.UserRepository;
import com.monitor.rest.service.IUserService;
import com.monitor.rest.validator.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final ObjectsValidator validator;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        validator.validate(userRequest);
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        

        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            return new UserResponse();
        }

        return userMapper.toUserResponse(userOptional.get());
    }

    @Override
    public UserResponse getUserByEmail(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (!userOptional.isPresent()) {
            return new UserResponse();
        }

        return userMapper.toUserResponse(userOptional.get());
    }

    /**
     * Email not update
     */
    @Override
    public UserResponse updateUser(Long userId, UserRequest user) {
        validator.validate(user);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return new UserResponse();
        }

        User userToUpdate = optionalUser.get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userToUpdate);

        return userMapper.toUserResponse(userToUpdate);
    }

    @Override
    public UserResponse deleteUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return new UserResponse();
        }

        userRepository.delete(optionalUser.get());
        return userMapper.toUserResponse(optionalUser.get());
    }

    @Override
    public List<PlantResponse> getUserWithPlants(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            return null;
        }
        
        UserWithPlants userWithPlants = userMapper.toUserWithPlants(user.get());
        return userWithPlants.getPlants();
    }

    @Override
    public TotalDataResponse getTotals(Long userId) {
        int numberOfReadings = 0, numberOfRedAlerts = 0, numberOfMediumAlerts = 0, numberOfDisabledSensors = 0;
        List<PlantResponse> plants = getUserWithPlants(userId);

        for (PlantResponse plant: plants) {
            numberOfReadings += plant.getReadingCount();
            numberOfRedAlerts += plant.getNumberOfRedAlerts();
            numberOfMediumAlerts += plant.getNumberOfMediumAlerts();
            numberOfDisabledSensors += plant.getNumberOfDisabledSensors();
        }

        return new TotalDataResponse(numberOfReadings, numberOfRedAlerts, numberOfMediumAlerts, numberOfDisabledSensors);
    }    
}
