package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "traceability_orders")
@Data
public class TraceabilityEntity {

    @Id
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
