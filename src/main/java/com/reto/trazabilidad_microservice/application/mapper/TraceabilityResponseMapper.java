package com.reto.trazabilidad_microservice.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.reto.trazabilidad_microservice.application.dto.TraceabilityResponse;
import com.reto.trazabilidad_microservice.domain.model.Traceability;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TraceabilityResponseMapper {

    TraceabilityResponse toResponse(Traceability traceability);

    default List <TraceabilityResponse> toResponseList(List<Traceability> traceabilityList){
        return traceabilityList.stream()
            .map(traceability -> {
                TraceabilityResponse traceabilityResponse = new TraceabilityResponse();
                traceabilityResponse.setId(traceability.getId());
                traceabilityResponse.setOrderId(traceability.getOrderId());
                traceabilityResponse.setClientDocument(traceability.getClientDocument());
                traceabilityResponse.setClientEmail(traceability.getClientEmail());
                traceabilityResponse.setDateTime(traceability.getDateTime());
                traceabilityResponse.setLastStatus(traceability.getLastStatus());
                traceabilityResponse.setNewStatus(traceability.getNewStatus());
                traceabilityResponse.setEmployeeDocument(traceability.getEmployeeDocument());
                traceabilityResponse.setEmployeeEmail(traceability.getEmployeeEmail());
                return traceabilityResponse;
            }).toList();
    }

    default Page<TraceabilityResponse> toResponsePage(Page<Traceability> traceabilityPage){
        List<TraceabilityResponse> responses= traceabilityPage.getContent().stream()
            .map(traceability -> {

                TraceabilityResponse traceabilityResponse = new TraceabilityResponse();
                traceabilityResponse.setId(traceability.getId());
                traceabilityResponse.setOrderId(traceability.getOrderId());
                traceabilityResponse.setClientDocument(traceability.getClientDocument());
                traceabilityResponse.setClientEmail(traceability.getClientEmail());
                traceabilityResponse.setDateTime(traceability.getDateTime());
                traceabilityResponse.setLastStatus(traceability.getLastStatus());
                traceabilityResponse.setNewStatus(traceability.getNewStatus());
                traceabilityResponse.setEmployeeDocument(traceability.getEmployeeDocument());
                traceabilityResponse.setEmployeeEmail(traceability.getEmployeeEmail());
                
                return traceabilityResponse;
            }).toList();
        return new PageImpl<>(responses, traceabilityPage.getPageable(), traceabilityPage.getTotalElements());
    }

}
