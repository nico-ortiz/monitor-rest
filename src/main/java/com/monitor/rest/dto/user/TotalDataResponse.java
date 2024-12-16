package com.monitor.rest.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDataResponse {
    
    private int totalOfReadings;

    private int totalOfRedAlerts;

    private int totalOfMediumAlerts;

    private int totalOfDisabledSensors;
}
