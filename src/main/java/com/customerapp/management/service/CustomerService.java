package com.customerapp.management.service;

import com.customerapp.management.dto.CustomerDTO;
import com.customerapp.management.entity.Customer;
import com.customerapp.management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id)
    {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id)
    {
        customerRepository.deleteById(id);
    }

    public Double getAverageAge()
    {
        return customerRepository.findAverageAge();
    }

    public List<Customer> getCustomersBetween18And40()
    {
        return customerRepository.findAll().stream()
                .filter(c -> c.getAge() >= 18 && c.getAge() <= 40)
                .collect(Collectors.toList());
    }

    public Optional<Customer> updateCustomer(Long id, CustomerDTO updatedCustomerDTO)
    {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(updatedCustomerDTO.name());
            existingCustomer.setEmail(updatedCustomerDTO.email());
            existingCustomer.setAge(updatedCustomerDTO.age());
            existingCustomer.setCity(updatedCustomerDTO.city());
            existingCustomer.setPhoneNumber(updatedCustomerDTO.phoneNumber());
            existingCustomer.setAddress(updatedCustomerDTO.address());
            existingCustomer.setActive(updatedCustomerDTO.isActive());

            return customerRepository.save(existingCustomer);
        });
    }

}
