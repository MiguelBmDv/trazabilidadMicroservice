package com.reto.trazabilidad_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phone;
    private Long nit;

}
