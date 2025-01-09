package com.reto.trazabilidad_microservice.infrastructure.input.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reto.trazabilidad_microservice.application.dto.TraceabilityRequest;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;
import com.reto.trazabilidad_microservice.application.handler.ITraceabilityHandler;
import com.reto.trazabilidad_microservice.domain.utils.JwtUtilsDomain;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.utils.TraceabilityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/traceability/")
@RequiredArgsConstructor
public class TraceabilityRestController {
    
    private final ITraceabilityHandler traceabilityHandler;
    private final TraceabilityUtils traceabilityUtils;
    private final JwtUtilsDomain jwtUtilsDomain;

    @PostMapping()
    public ResponseEntity<Void> saveTraceabilityInTraceability(@RequestBody TraceabilityRequest traceabilityRequest){
        traceabilityHandler.saveTraceabilityinTraceability(traceabilityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("my-orders")
    public ResponseEntity<Page<TraceabilityResponse>> getTraceabilityByUser(
            @RequestParam(required = false) String lastStatus,
            @RequestParam(required = false) String newStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        
        Long documentNumber = jwtUtilsDomain.extractIdFromToken();
        traceabilityUtils.validateTraceabilityByUser(documentNumber);

        return ResponseEntity.ok(traceabilityHandler.getTraceabilityByUser(documentNumber, lastStatus, newStatus, page, size));

    }

}
