package com.adraga_mesas.reservasrestaurante.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Configuration
@ConfigurationProperties(prefix = "restaurant")
@Getter
@Setter
public class RestaurantConfig {
    private LocalTime openingTime = LocalTime.of(11, 0); // 11:00 AM
    private LocalTime closingTime = LocalTime.of(23, 0); // 11:00 PM
    private int maxReservationDurationHours = 2;
    private int minReservationNoticeHours = 1;
} 