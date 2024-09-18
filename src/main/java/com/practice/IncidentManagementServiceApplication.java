package com.practice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Incident Management", version = "1.0"))
//@EnableDiscoveryClient(autoRegister = true)
public class IncidentManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentManagementServiceApplication.class, args);
	}

}
