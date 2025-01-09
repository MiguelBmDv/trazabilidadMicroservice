package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.reto.trazabilidad_microservice.domain.spi.JwtTokenPersistencePort;
import com.reto.trazabilidad_microservice.infrastructure.security.JwtSecurityContext;

@Component
public class JwtUtils implements JwtTokenPersistencePort {
    
   @Override
    public Long extractDocumentNumberFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            JwtSecurityContext jwtSecurityContext = JwtSecurityContext.getContext();

            if (jwtSecurityContext != null) {
                return jwtSecurityContext.getDocumentNumber();
            }
        }

        throw new IllegalStateException("No se pudo extraer el documentNumber del JWT");
    }
    
}
