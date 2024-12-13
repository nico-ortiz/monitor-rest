package com.monitor.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.rest.dto.plant.PlantRequest;
import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.plant.PlantWithUser;
import com.monitor.rest.service.IPlantService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/plants")
public class PlantController {
    
    private IPlantService plantService;

    @PostMapping("/create")
    public ResponseEntity<PlantResponse> createPlant(@RequestBody PlantRequest request) {
        PlantResponse plant = plantService.createPlant(request);

        if (plant.getPlantId() == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(plant, HttpStatus.CREATED);
    }

    @GetMapping("/{plantId}")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable Long plantId) {
        PlantResponse plantResponse = plantService.getPlantById(plantId);

        if (plantResponse.getPlantId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plantResponse, HttpStatus.OK);
    } 

    @GetMapping("/all-info/{plantId}")
    public ResponseEntity<PlantWithUser> getAllInfoPlantById(@PathVariable Long plantId) {
        PlantWithUser plantResponse = plantService.getAllInfoPlant(plantId);

        if (plantResponse.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plantResponse, HttpStatus.OK);
    }
    
    @GetMapping("/by-name/{plantName}")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable String plantName) {
        PlantResponse plantResponse = plantService.getPlantByName(plantName);

        if (plantResponse.getPlantId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plantResponse, HttpStatus.OK);
    } 

    @PutMapping("/update/{plantId}")
    public ResponseEntity<PlantResponse> updatePlant(@PathVariable Long plantId, @RequestBody PlantRequest request) {
        PlantResponse plantResponse = plantService.updatePlantById(plantId, request);
        
        if (plantResponse.getPlantId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plantResponse, HttpStatus.OK);
    }

    @DeleteMapping("/del/{plantId}")
    public ResponseEntity<PlantWithUser> deletePlantById(@PathVariable Long plantId) {
        PlantWithUser plantDeleted = plantService.deletePlantById(plantId);

        if (plantDeleted.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(plantDeleted, HttpStatus.OK);
    }

}
