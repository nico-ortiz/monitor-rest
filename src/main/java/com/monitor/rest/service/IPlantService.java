package com.monitor.rest.service;

import com.monitor.rest.dto.plant.PlantRequest;
import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.plant.PlantWithUser;

public interface IPlantService {
    
    PlantResponse createPlant(PlantRequest plant);

    PlantResponse getPlantById(Long plantId);

    PlantWithUser getAllInfoPlant(Long plantId);

    PlantResponse getPlantByName(String name);

    PlantResponse updatePlantById(Long plantId, PlantRequest request);

    PlantWithUser deletePlantById(Long plantId);
}
