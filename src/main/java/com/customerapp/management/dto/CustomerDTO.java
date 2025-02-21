package com.customerapp.management.dto;

import jakarta.validation.constraints.*;
import com.customerapp.management.entity.Customer;

public record CustomerDTO(
        @NotBlank(message = "Name is required") String name,
        @Email(message = "Invalid email format") String email,
        @Min(value = 18, message = "Age must be at least 18") int age,
        @NotBlank(message = "City is required") String city,
        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") String phoneNumber,
        String address,
        Boolean isActive
) {
    public Customer toEntity()
    {
        return new Customer(this);
    }
}
