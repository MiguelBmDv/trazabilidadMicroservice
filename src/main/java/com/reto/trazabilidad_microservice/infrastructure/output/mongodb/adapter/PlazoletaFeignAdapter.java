package com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.reto.trazabilidad_microservice.application.dto.OrderResponse;
import com.reto.trazabilidad_microservice.application.dto.RestaurantResponse;
import com.reto.trazabilidad_microservice.domain.model.Order;
import com.reto.trazabilidad_microservice.domain.model.Restaurant;
import com.reto.trazabilidad_microservice.domain.spi.IOrderPersistencePort;
import com.reto.trazabilidad_microservice.domain.spi.IRestaurantPersistencePort;
import com.reto.trazabilidad_microservice.infrastructure.output.mongodb.adapter.client.PlazoletaFeignClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlazoletaFeignAdapter implements IRestaurantPersistencePort, IOrderPersistencePort{
    
    private final PlazoletaFeignClient plazoletaFeignClient;

    @Override
    public Page<Order> getOrdersByRestaurant(Long restaurantId, String status, int page, int size) {
        Page<OrderResponse> orderResponsePage = plazoletaFeignClient.getOrdersByRestaurant(status, page, size);
        return orderResponsePage.map(this::toDomainOrder);
    }

    @Override
    public Restaurant getRestaurant(Long nit) {
        RestaurantResponse restaurantResponse = plazoletaFeignClient.getRestaurantByNit(nit);
        return toDomainRestaurantInfo(restaurantResponse);
    }

    public Restaurant toDomainRestaurantInfo(RestaurantResponse restaurantResponse){
        return new Restaurant(restaurantResponse.getId(), 
                            restaurantResponse.getName(), 
                            restaurantResponse.getAddress(), 
                            restaurantResponse.getOwnerId(), 
                            restaurantResponse.getPhone(), 
                            restaurantResponse.getNit());
    }

    public Order toDomainOrder(OrderResponse orderResponse) {
        return new Order(
                orderResponse.getId(),
                orderResponse.getClientId(),
                orderResponse.getDate(),
                orderResponse.getStatus(),
                orderResponse.getChefId(),
                orderResponse.getRestaurantId()
        );
    }

}
