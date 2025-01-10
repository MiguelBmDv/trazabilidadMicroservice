package com.reto.trazabilidad_microservice.application.handler;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.trazabilidad_microservice.application.dto.EmployeeRankingResponse;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityRequest;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;

public interface ITraceabilityHandler {

    void saveTraceabilityinTraceability(TraceabilityRequest traceabilityRequest);

    List<TraceabilityResponse> getAllTraceabilityFromTraceability();

    TraceabilityResponse getTraceabilityFromTraceability(Long id);

    Page<TraceabilityResponse> getTraceabilityByUser(Long clientDocument, String lastStatus, String newStatus, int page, int size);

    void validateOwnerAccess(Long nit, Long ownerId);

    List<TraceabilityResponse> getOrderTraceability(Long nit, Long orderId);

    List<EmployeeRankingResponse> getEmployeeRanking(Long nit, Long employeeId);

}
