package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.reto.trazabilidad_microservice.domain.model.Traceability;
import com.reto.trazabilidad_microservice.domain.spi.ITraceabilityPersistencePort;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.entity.TraceabilityEntity;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.mapper.TraceabilityEntityMapper;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.repository.ITraceabilityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TraceabilityMongodbAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityRepository traceabilityRepository;
    private final TraceabilityEntityMapper traceabilityEntityMapper;
    
    @Override
    public void saveTraceability(Traceability traceability) {
        traceabilityRepository.save(traceabilityEntityMapper.toEntity(traceability));
    }

    @Override
    public List<Traceability> getAllTraceability() {
        List<TraceabilityEntity> traceabilityEntityList = traceabilityRepository.findAll();
        return traceabilityEntityMapper.toTraceabilityList(traceabilityEntityList);
    }

    @Override
    public Traceability getTraceability(Long id) {
        return traceabilityEntityMapper.toTraceability(traceabilityRepository.findById(id)
            .orElseThrow());   
    }

    @Override
    public Page<Traceability> getTraceabilityByUser(Long clientDocument, String lastStatus, String newStatus, int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TraceabilityEntity> traceabilityEntityPage;
        
        if (lastStatus != null && !lastStatus.isEmpty() && newStatus != null && !newStatus.isEmpty()) {
            traceabilityEntityPage = traceabilityRepository.findByClientDocumentAndLastStatusAndNewStatus(
                clientDocument, lastStatus, newStatus, pageable);
        } else if (lastStatus != null && !lastStatus.isEmpty()) {
            traceabilityEntityPage = traceabilityRepository.findByClientDocumentAndLastStatus(
                clientDocument, lastStatus, pageable);
        } else if (newStatus != null && !newStatus.isEmpty()) {
            traceabilityEntityPage = traceabilityRepository.findByClientDocumentAndNewStatus(
                clientDocument, newStatus, pageable);
        } else {
            traceabilityEntityPage = traceabilityRepository.findByClientDocument(clientDocument, pageable);
        }
        return traceabilityEntityPage.map(traceabilityEntityMapper::toTraceability);
    }

}
