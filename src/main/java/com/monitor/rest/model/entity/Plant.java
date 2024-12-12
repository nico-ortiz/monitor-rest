package com.monitor.rest.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "plants")
public class Plant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String country;

    private int readingCount;

    private int numberOfRedAlerts;

    private int numberOfMediumAlerts;

    private int numberOfDisabledSensors;

    @ManyToOne()
    @JoinColumn(name = "monitored_by")
    private User user;
}
