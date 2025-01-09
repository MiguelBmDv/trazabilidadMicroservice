package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.entity.TraceabilityEntity;

public interface ITraceabilityRepository extends MongoRepository <TraceabilityEntity, Long> {

    List <TraceabilityEntity> findByClientDocument(Long clientDocument);

    Page<TraceabilityEntity> findByClientDocument(Long clientDocument, Pageable pageable);

    Page<TraceabilityEntity> findByClientDocumentAndLastStatus(Long clientDocument, String lastStatus, Pageable pageable);

    Page<TraceabilityEntity> findByClientDocumentAndNewStatus(Long clientDocument, String newStatus, Pageable pageable);

    Page<TraceabilityEntity> findByClientDocumentAndLastStatusAndNewStatus(Long clientDocument, String lastStatus, String newStatus, Pageable pageable);

}
