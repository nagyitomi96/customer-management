package com.customerapp.management.controller;

import com.customerapp.management.dto.CustomerDTO;
import com.customerapp.management.entity.Customer;
import com.customerapp.management.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerDTO customerDTO)
    {
        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setCity(customerDTO.city());
        customer.setPhoneNumber(customerDTO.phoneNumber());
        customer.setAddress(customerDTO.address());
        customer.setActive(customerDTO.isActive());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customer));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody @Valid CustomerDTO updatedCustomerDTO
    ) {
        return customerService.updateCustomer(id, updatedCustomerDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)
    {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge()
    {
        return ResponseEntity.ok(customerService.getAverageAge());
    }

    @GetMapping("/age-range")
    public ResponseEntity<List<Customer>> getCustomersBetween18And40()
    {
        return ResponseEntity.ok(customerService.getCustomersBetween18And40());
    }
}
