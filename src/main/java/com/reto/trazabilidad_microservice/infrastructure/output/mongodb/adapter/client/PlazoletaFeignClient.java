package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.reto.trazabilidad_microservice.application.dto.OrderResponse;
import com.reto.trazabilidad_microservice.application.dto.RestaurantResponse;
import com.reto.trazabilidad_microservice.infrastructure.configuration.FeignClientConfig;

@FeignClient(name="plazoleta-microservice", url= "http://localhost:8091/", configuration = FeignClientConfig.class)
public interface PlazoletaFeignClient {

    @GetMapping("owner/food-court/{nitRestaurant}")
    RestaurantResponse getRestaurantByNit(@PathVariable("nitRestaurant") Long nit);

    @GetMapping("orders/")
    Page<OrderResponse> getOrdersByRestaurant(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);


}
