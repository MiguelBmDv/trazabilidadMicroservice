package com.reto.trazabilidad_microservice.application.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
