package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.reto.trazabilidad_microservice.domain.model.Traceability;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.entity.TraceabilityEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TraceabilityEntityMapper {

    TraceabilityEntity toEntity (Traceability traceability);

    Traceability toTraceability(TraceabilityEntity traceabilityEntity);

    List<Traceability> toTraceabilityList(List<TraceabilityEntity> traceabilityEntityList);

}
