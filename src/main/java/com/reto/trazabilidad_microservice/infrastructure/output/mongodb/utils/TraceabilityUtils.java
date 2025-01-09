package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reto.trazabilidad_microservice.infrastructure.exception.UserNotFoundException;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.entity.TraceabilityEntity;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.repository.ITraceabilityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TraceabilityUtils {

    private final ITraceabilityRepository traceabilityRepository;

    public void validateTraceabilityByUser(Long documentNumber) {
        List<TraceabilityEntity> traceabilityClients = traceabilityRepository.findByClientDocument(documentNumber);

        if (traceabilityClients.isEmpty()) {
            throw new UserNotFoundException();
        }
    }

}
