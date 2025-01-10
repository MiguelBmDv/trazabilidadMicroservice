package com.reto.trazabilidad_microservice.infrastructure.input.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.trazabilidad_microservice.application.dto.EmployeeRankingResponse;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;
import com.reto.trazabilidad_microservice.application.handler.ITraceabilityHandler;
import com.reto.trazabilidad_microservice.domain.utils.JwtUtilsDomain;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/owner/traceability")
@RequiredArgsConstructor
public class OwnerTraceabilityRestController {

    private final ITraceabilityHandler traceabilityHandler;
    private final JwtUtilsDomain jwtUtilsDomain;

    @GetMapping("/{nit}/{orderId}")
    public ResponseEntity<List<TraceabilityResponse>> getOrderTraceability(
            @PathVariable Long nit,
            @PathVariable Long orderId) {

        Long ownerId = jwtUtilsDomain.extractIdFromToken();

        traceabilityHandler.validateOwnerAccess(nit, ownerId);
        List<TraceabilityResponse> traceability = traceabilityHandler.getOrderTraceability(nit, orderId);

        return ResponseEntity.ok(traceability);
    }

    @GetMapping("/{nit}/ranking/{employee}")
    public ResponseEntity<List<EmployeeRankingResponse>> getEmployeeRanking(
            @PathVariable Long nit,
            @PathVariable Long employee) {

        Long ownerId = jwtUtilsDomain.extractIdFromToken();
        traceabilityHandler.validateOwnerAccess(nit, ownerId);

        List<EmployeeRankingResponse> ranking = traceabilityHandler.getEmployeeRanking(nit, employee);
        
        return ResponseEntity.ok(ranking);
    }
}
