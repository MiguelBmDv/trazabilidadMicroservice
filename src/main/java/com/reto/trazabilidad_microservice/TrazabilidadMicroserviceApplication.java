package com.reto.trazabilidad_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TrazabilidadMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrazabilidadMicroserviceApplication.class, args);
	}

}
