package com.monitor.rest.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantWithUser {
        
    private Long id;

    private String name;

    private String country;

    private int readingCount;

    private int numberOfRedAlerts;

    private int numberOfMediumAlerts;

    private int numberOfDisabledSensors;

    private Long userId;
}
