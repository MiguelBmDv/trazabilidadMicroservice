package com.reto.trazabilidad_microservice.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRankingResponse {
    private Long employeeId;
    private Long orderId;
    private String formattedDuration;

    public EmployeeRankingResponse(Long employeeId, Long orderId, String formattedDuration) {
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.formattedDuration = formattedDuration;
    }
}
