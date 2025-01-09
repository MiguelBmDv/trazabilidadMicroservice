package com.reto.trazabilidad_microservice.domain.api;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.trazabilidad_microservice.domain.model.Traceability;

public interface ITraceabilityServicePort {

    void saveTraceability(Traceability traceability);

    List<Traceability> getAllTraceability();

    Traceability getTraceability(Long id);

    Page <Traceability> getTraceabilityByUser(Long clientDocument, String lastStatus, String newStatus, int page, int size);

}
