package com.reto.trazabilidad_microservice.application.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    private Long id;
    private Long clientId;
    private LocalDate date;
    private String status;
    private Long chefId;
    private Long restaurantId;

}
