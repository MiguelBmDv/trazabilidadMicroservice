package com.reto.trazabilidad_microservice.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.reto.trazabilidad_microservice.domain.api.ITraceabilityServicePort;
import com.reto.trazabilidad_microservice.domain.spi.ITraceabilityPersistencePort;
import com.reto.trazabilidad_microservice.domain.usecase.TraceabilityUseCase;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter.TraceabilityMongodbAdapter;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.mapper.TraceabilityEntityMapper;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.repository.ITraceabilityRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITraceabilityRepository traceabilityRepository;
    private final TraceabilityEntityMapper traceabilityEntityMapper;

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort(){
        return new TraceabilityMongodbAdapter(traceabilityRepository, traceabilityEntityMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort(){
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }

}
