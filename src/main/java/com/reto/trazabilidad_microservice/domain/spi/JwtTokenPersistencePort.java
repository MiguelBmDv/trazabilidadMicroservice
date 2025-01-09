package com.reto.trazabilidad_microservice.domain.spi;

public interface JwtTokenPersistencePort {
    Long extractDocumentNumberFromToken();
}   
