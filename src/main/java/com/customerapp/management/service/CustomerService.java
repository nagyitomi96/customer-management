package com.customerapp.management.service;

import com.customerapp.management.dto.CustomerDTO;
import com.customerapp.management.entity.Customer;
import com.customerapp.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers()
    {
        return customerRepository.findAll().stream()
                .map(Customer::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(Long id)
    {
        return customerRepository.findById(id)
                .map(Customer::toDTO);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO)
    {
        Customer customer = customerDTO.toEntity();
        customer = customerRepository.save(customer);
        return customer.toDTO();
    }

    public boolean deleteCustomer(Long id)
    {
        if (!customerRepository.existsById(id))
        {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }

    public Float getAverageAge()
    {
        return customerRepository.findAverageAge();
    }

    public List<Customer> getCustomersBetween18And40()
    {
        return customerRepository.findAll().stream()
                .filter(c -> c.getAge() >= 18 && c.getAge() <= 40)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> updateCustomer(Long id, CustomerDTO updatedCustomerDTO)
    {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.updateFromDTO(updatedCustomerDTO);
            return customerRepository.save(existingCustomer).toDTO();
        });
    }

}
