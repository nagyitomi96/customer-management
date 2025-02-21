package com.customerapp.management.controller;

import com.customerapp.management.dto.CustomerDTO;
import com.customerapp.management.entity.Customer;
import com.customerapp.management.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id)
    {
        return customerService.getCustomerById(id).
                                orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody @Valid CustomerDTO customerDTO)
    {
        return customerService.createCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(
            @PathVariable Long id,
            @RequestBody @Valid CustomerDTO updatedCustomerDTO
    ) {
        return customerService.updateCustomer(id, updatedCustomerDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id)
    {
        boolean deleted = customerService.deleteCustomer(id);

        if (!deleted)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }

    @GetMapping("/average-age")
    public Float getAverageAge()
    {
        return customerService.getAverageAge();
    }

    @GetMapping("/age-range")
    public List<Customer>getCustomersBetween18And40()
    {
        return customerService.getCustomersBetween18And40();
    }
}
