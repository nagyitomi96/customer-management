package com.customerapp.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Customer Management API", version = "1.0", description = "API for managing customers"))
public class CustomerManagementApplication
{
	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}
}
