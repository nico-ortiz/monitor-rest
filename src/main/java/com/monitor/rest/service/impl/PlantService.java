package com.monitor.rest.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monitor.rest.dto.plant.PlantRequest;
import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.plant.PlantWithUser;
import com.monitor.rest.mapper.PlantMapper;
import com.monitor.rest.model.Plant;
import com.monitor.rest.repository.PlantRepository;
import com.monitor.rest.service.IPlantService;
import com.monitor.rest.validator.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantService implements IPlantService {

    private final PlantRepository plantRepository;

    private final PlantMapper plantMapper;

    private final ObjectsValidator validator;

    @Override
    public PlantResponse createPlant(PlantRequest plant) {
        validator.validate(plant);
        Optional<Plant> optionalPlant = plantRepository.findByNameAndCountry(plant.getName(), plant.getCountry());

        //Exists plant with name and country
        if (optionalPlant.isPresent()) {
            return new PlantResponse();
        }
        
        plant.setReadingCount(0);
        plant.setNumberOfRedAlerts(0);
        plant.setNumberOfMediumAlerts(0);
        plant.setNumberOfDisabledSensors(0);
        
        Plant plantSaved = plantRepository.save(plantMapper.toPlant(plant));
        return plantMapper.toPlantResponse(plantSaved);
    }

    @Override
    public PlantResponse getPlantById(Long plantId) {
        Optional<Plant> optionalPlant = plantRepository.findById(plantId);

        if (!optionalPlant.isPresent()) {
            return new PlantResponse();
        }

        return plantMapper.toPlantResponse(optionalPlant.get());
    }

    @Override
    public PlantWithUser getAllInfoPlant(Long plantId) {
        Optional<Plant> optionalPlant = plantRepository.findById(plantId);

        if (!optionalPlant.isPresent()) {
            return new PlantWithUser();
        }

        return plantMapper.toPlantWithUser(optionalPlant.get());
    }

    @Override
    public PlantResponse getPlantByName(String name) {
        Optional<Plant> optionalPlant = plantRepository.findByName(name);

        if (!optionalPlant.isPresent()) {
            return new PlantResponse();
        }

        return plantMapper.toPlantResponse(optionalPlant.get());
    }

    @Override
    public PlantResponse updatePlantById(Long plantId, PlantRequest request) {
        PlantResponse plantToUpdate = getPlantById(plantId);

        if (plantToUpdate.getPlantId() == null) {
            return new PlantResponse();
        }

        validator.validate(request);
        Plant plant = plantMapper.toPlant(plantToUpdate);

        plant.setName(request.getName());
        plant.setCountry(request.getCountry());
        plant.setReadingCount(request.getReadingCount());
        plant.setNumberOfRedAlerts(request.getNumberOfRedAlerts());
        plant.setNumberOfMediumAlerts(request.getNumberOfMediumAlerts());
        plant.setNumberOfDisabledSensors(request.getNumberOfDisabledSensors());
        plantRepository.save(plant);

        return plantMapper.toPlantResponse(plant);
    }

    @Override
    public PlantWithUser deletePlantById(Long plantId) {
        Optional<Plant> optionalPlant = plantRepository.findById(plantId);

        if (!optionalPlant.isPresent()) {
            return new PlantWithUser();
        }

        plantRepository.delete(optionalPlant.get());
        return plantMapper.toPlantWithUser(optionalPlant.get());
    }
    
}
