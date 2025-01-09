package com.reto.trazabilidad_microservice.application.handler;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reto.trazabilidad_microservice.application.dto.TraceabilityRequest;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;
import com.reto.trazabilidad_microservice.application.mapper.TraceabilityRequestMapper;
import com.reto.trazabilidad_microservice.application.mapper.TraceabilityResponseMapper;
import com.reto.trazabilidad_microservice.domain.api.ITraceabilityServicePort;
import com.reto.trazabilidad_microservice.domain.model.Traceability;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TraceabilityHandler implements ITraceabilityHandler {
    
    private final ITraceabilityServicePort traceabilityServicePort;
    private final TraceabilityRequestMapper traceabilityRequestMapper;
    private final TraceabilityResponseMapper traceabilityResponseMapper;
    
    @Override
    public void saveTraceabilityinTraceability(TraceabilityRequest traceabilityRequest) {
        Traceability traceability = traceabilityRequestMapper.toTraceability(traceabilityRequest);
        traceabilityServicePort.saveTraceability(traceability);
    }

    @Override
    public List<TraceabilityResponse> getAllTraceabilityFromTraceability() {
        return traceabilityResponseMapper.toResponseList(traceabilityServicePort.getAllTraceability());
    }

    @Override
    public TraceabilityResponse getTraceabilityFromTraceability(Long id) {
        Traceability traceability = traceabilityServicePort.getTraceability(id);
        TraceabilityResponse traceabilityResponse = traceabilityResponseMapper.toResponse(traceability);
        return traceabilityResponse;
    }

    @Override
    public Page<TraceabilityResponse> getTraceabilityByUser(Long clientDocument, String lasStatus, String newStatus, int page, int size){
        Page<Traceability> traceabilityPage = traceabilityServicePort.getTraceabilityByUser(clientDocument, lasStatus, newStatus, page, size);
        return traceabilityResponseMapper.toResponsePage(traceabilityPage);
    }
    
}
