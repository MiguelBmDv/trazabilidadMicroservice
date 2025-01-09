package com.reto.trazabilidad_microservice.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.trazabilidad_microservice.application.dto.TraceabilityRequest;
import com.reto.trazabilidad_microservice.domain.model.Traceability;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TraceabilityRequestMapper {

    Traceability toTraceability(TraceabilityRequest traceabilityRequest);
    
}
