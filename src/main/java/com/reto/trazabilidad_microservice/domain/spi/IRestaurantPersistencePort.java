package com.reto.trazabilidad_microservice.domain.spi;

import com.reto.trazabilidad_microservice.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    Restaurant getRestaurant(Long nit);
}
