package com.monitor.rest.dto.user;

import java.util.List;

import com.monitor.rest.dto.plant.PlantResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithPlants {
    
    private Long userId;
    
    private String firstName;

    private String lastName;

    private String email;

    private List<PlantResponse> plants;
}
