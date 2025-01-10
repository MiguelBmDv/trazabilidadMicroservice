package com.reto.trazabilidad_microservice.application.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reto.trazabilidad_microservice.application.dto.EmployeeRankingResponse;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityRequest;
import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;
import com.reto.trazabilidad_microservice.application.mapper.TraceabilityRequestMapper;
import com.reto.trazabilidad_microservice.application.mapper.TraceabilityResponseMapper;
import com.reto.trazabilidad_microservice.domain.api.ITraceabilityServicePort;
import com.reto.trazabilidad_microservice.domain.model.Restaurant;
import com.reto.trazabilidad_microservice.domain.model.Traceability;
import com.reto.trazabilidad_microservice.domain.spi.IRestaurantPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TraceabilityHandler implements ITraceabilityHandler {
    
    private final ITraceabilityServicePort traceabilityServicePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
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

    @Override
    public void validateOwnerAccess(Long nit, Long ownerId) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(nit);
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new AccessDeniedException("El usuario no es el dueño del restaurante.");
        }
    }

    @Override
    public List<TraceabilityResponse> getOrderTraceability(Long nit, Long orderId) {
        List<Traceability> traceabilityRecords = traceabilityServicePort.getAllTraceability();
        List<TraceabilityResponse> traceabilityResponses = traceabilityResponseMapper.toResponseList(traceabilityRecords);
        List<TraceabilityResponse> orderTraceability = traceabilityResponses.stream()
                .filter(response -> response.getOrderId().equals(orderId))
                .sorted(Comparator.comparing(TraceabilityResponse::getDateTime)) 
                .collect(Collectors.toList());

        LocalDateTime start = null;
        LocalDateTime end = null;

        for (TraceabilityResponse response : orderTraceability) {
            if (response.getNewStatus().equals("PENDIENTE") && start == null) {
                start = response.getDateTime(); 
            }
            if (response.getNewStatus().equals("CANCELADO") || response.getNewStatus().equals("ENTREGADO")) {
                end = response.getDateTime();
            }
        }

        if (start != null && end != null) {
            long duration = Duration.between(start, end).toMillis();
            TraceabilityResponse traceabilityResponse = new TraceabilityResponse();
            traceabilityResponse.setOrderId(orderId);
            traceabilityResponse.setNewStatus(formatDuration(duration)); 
        
            return Collections.singletonList(traceabilityResponse);
        } else {
            return Collections.emptyList(); 
        }
    }

    private String formatDuration(long duration) {
        long hours = duration / (1000 * 60 * 60);
        long minutes = (duration / (1000 * 60)) % 60;
        long seconds = (duration / 1000) % 60;
        return String.format("Duracion del pedido: %02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public List<EmployeeRankingResponse> getEmployeeRanking(Long nit, Long employeeId) {
        List<Traceability> traceabilityRecords = traceabilityServicePort.getAllTraceability();
        List<TraceabilityResponse> traceabilityResponses = traceabilityResponseMapper.toResponseList(traceabilityRecords);
    
        // Filtrar las trazas relacionadas con el empleado
        List<TraceabilityResponse> employeeTraceability = traceabilityResponses.stream()
                .filter(response -> response.getEmployeeDocument() != null && response.getEmployeeDocument().equals(employeeId))
                .collect(Collectors.toList());
    
        // Agrupar las órdenes y calcular el tiempo formateado por cada una
        Map<Long, String> orderDurations = employeeTraceability.stream()
                .collect(Collectors.groupingBy(
                        TraceabilityResponse::getOrderId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                tracesForOrder -> {
                                    LocalDateTime start = tracesForOrder.stream()
                                            .filter(t -> "EN PROCESO".equals(t.getNewStatus()))
                                            .map(TraceabilityResponse::getDateTime)
                                            .findFirst().orElse(null);
    
                                    LocalDateTime end = tracesForOrder.stream()
                                            .filter(t -> "CANCELADO".equals(t.getNewStatus()) || "ENTREGADO".equals(t.getNewStatus()))
                                            .map(TraceabilityResponse::getDateTime)
                                            .reduce((_, second) -> second).orElse(null);
    
                                    if (start != null && end != null) {
                                        long duration = Duration.between(start, end).toMillis();
                                        return formatDurationChefRanking(duration); // Formato en horas:minutos:segundos
                                    } else {
                                        return "Duracion no disponible";
                                    }
                                })));
    
        return orderDurations.entrySet().stream()
                .map(entry -> new EmployeeRankingResponse(employeeId, entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(response -> parseDurationToMillis(response.getFormattedDuration())))
                .collect(Collectors.toList());
    }

    private String formatDurationChefRanking(long duration) {
        long hours = duration / (1000 * 60 * 60);
        long minutes = (duration / (1000 * 60)) % 60;
        long seconds = (duration / 1000) % 60;
        return String.format("Duracion del chef en pedido: %02d:%02d:%02d", hours, minutes, seconds);
    }

    
    private long parseDurationToMillis(String formattedDuration) {
        if (formattedDuration.startsWith("Duracion no disponible")) {
            return Long.MAX_VALUE; 
        }
        String[] parts = formattedDuration.replace("Duracion del chef en pedido: ", "").split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return (hours * 3600 + minutes * 60 + seconds) * 1000;
    }
    
    
}
