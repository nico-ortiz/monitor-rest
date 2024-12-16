package com.monitor.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.monitor.rest.dto.plant.PlantRequest;
import com.monitor.rest.dto.plant.PlantResponse;
import com.monitor.rest.dto.plant.PlantWithUser;
import com.monitor.rest.model.Plant;

@Mapper(componentModel = "spring")
public interface PlantMapper {
    
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "user.id", source = "userId")
    })
    Plant toPlant(PlantRequest plantRequest);

    @Mappings({
        @Mapping(target = "user", ignore = true),
        @Mapping(target = "id", source = "plantId")
    })
    Plant toPlant(PlantResponse plantResponse);

    @Mapping(target = "plantId", source = "id")
    PlantResponse toPlantResponse(Plant plant);

    List<PlantResponse> toSetPlantResponse(List<Plant> plants);

    @Mapping(target = "userId", source = "user.id")
    PlantWithUser toPlantWithUser(Plant plant);
}
