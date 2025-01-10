package com.reto.trazabilidad_microservice.domain.spi;

import org.springframework.data.domain.Page;

import com.reto.trazabilidad_microservice.domain.model.Order;

public interface IOrderPersistencePort {

    Page<Order> getOrdersByRestaurant(Long restaurantId, String status, int page, int size);
}
