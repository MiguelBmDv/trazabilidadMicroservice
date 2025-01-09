package com.reto.trazabilidad_microservice.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TraceabilityResponse {

    private Long id;
    private Long orderId;
    private Long clientDocument;
    private String clientEmail;
    private LocalDateTime dateTime;
    private String lastStatus;
    private String newStatus;
    private Long employeeDocument;
    private String employeeEmail;

}
