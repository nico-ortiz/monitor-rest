package com.monitor.rest.dto.plant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantRequest {

    @NotBlank(message = "Es requerido el nombre de la planta")
    private String name;

    @NotBlank(message = "El pais de la planta es requerido")
    private String country;

    private int readingCount;

    private int numberOfRedAlerts;

    private int numberOfMediumAlerts;

    private int numberOfDisabledSensors;

    private Long userId;
}
