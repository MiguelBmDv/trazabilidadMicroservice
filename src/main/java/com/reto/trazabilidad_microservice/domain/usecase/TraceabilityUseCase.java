package com.reto.trazabilidad_microservice.domain.usecase;

import java.util.List;

import org.springframework.data.domain.Page;

import com.reto.trazabilidad_microservice.domain.api.ITraceabilityServicePort;
import com.reto.trazabilidad_microservice.domain.model.Traceability;
import com.reto.trazabilidad_microservice.domain.spi.ITraceabilityPersistencePort;

public class TraceabilityUseCase implements ITraceabilityServicePort{

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort){
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void saveTraceability(Traceability traceability) {
        traceabilityPersistencePort.saveTraceability(traceability);
    }

    @Override
    public List<Traceability> getAllTraceability() {
        return traceabilityPersistencePort.getAllTraceability();
    }

    @Override
    public Traceability getTraceability(Long id) {
        return traceabilityPersistencePort.getTraceability(id);
    }

    @Override
    public Page<Traceability> getTraceabilityByUser(Long clientDocument, String lastStatus, String newStatus, int page,
            int size) {
        return traceabilityPersistencePort.getTraceabilityByUser(clientDocument, lastStatus, newStatus, page, size);
    }

}
