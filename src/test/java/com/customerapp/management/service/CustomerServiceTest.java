package com.customerapp.management.service;

import com.customerapp.management.dto.CustomerDTO;
import com.customerapp.management.entity.Customer;
import com.customerapp.management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest
{

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetAllCustomers()
    {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertThat(result).hasSize(2);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById_CustomerExists()
    {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> result = customerService.getCustomerById(1L);

        assertThat(result).isPresent();
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerById_CustomerNotExists()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.getCustomerById(1L);

        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCustomer()
    {
        CustomerDTO customerDTO = new CustomerDTO(
                "Test User 1",
                "user1@example.com",
                25,
                "City1",
                "+36101234567",
                "Address 1",
                true
        );
        Customer customer = customerDTO.toEntity();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);

        assertThat(createdCustomer).isNotNull();
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer_CustomerExists()
    {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        boolean result = customerService.deleteCustomer(1L);

        assertThat(result).isTrue();
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCustomer_CustomerNotExists()
    {
        when(customerRepository.existsById(1L)).thenReturn(false);

        boolean result = customerService.deleteCustomer(1L);

        assertThat(result).isFalse();
        verify(customerRepository, times(1)).existsById(1L);
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAverageAge()
    {
        when(customerRepository.findAverageAge()).thenReturn(30.5f);

        Float result = customerService.getAverageAge();

        assertThat(result).isEqualTo(30.5f);
        verify(customerRepository, times(1)).findAverageAge();
    }

    @Test
    void testGetCustomersBetween18And40()
    {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "Test User 1", "user1@example.com", 25,
                        "City1", "+36701111111", "Address 1", true, new Date()),

                new Customer(2L, "Test User 2", "user2@example.com", 22,
                        "City2", "+36702222222", "Address 2", true, new Date()),

                new Customer(3L, "Test User 3", "user3@example.com", 35,
                        "City3", "+36703333333", "Address 3", true, new Date()),

                new Customer(4L, "Test User 4", "user4@example.com", 19,
                        "City4", "+36704444444", "Address 4", true, new Date()),

                new Customer(5L, "Test User 5", "user5@example.com", 45,
                        "City5", "+36705555555", "Address 5", true, new Date()),

                new Customer(6L, "Test User 6", "user6@example.com", 50,
                        "City6", "+36706666666", "Address 6", true, new Date()),

                new Customer(7L, "Test User 7", "user7@example.com", 17,
                        "City7", "+36707777777", "Address 7", true, new Date()),

                new Customer(8L, "Test User 8", "user8@example.com", 30,
                        "City8", "+36708888888", "Address 8", true, new Date())
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomersBetween18And40();

        assertThat(result).hasSize(5);
        assertThat(result.stream().allMatch(customer -> customer.getAge() >= 18 && customer.getAge() <= 40)).isTrue();

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer_CustomerExists()
    {
        Customer existingCustomer = new Customer(1L, "Old Name", "old@example.com", 40,
                "City1", "+36701111111", "Old Address", true, new Date());

        CustomerDTO updatedDTO = new CustomerDTO("New Name", "new@example.com", 30,
                "City2", "+36702222222", "New Address", true);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<CustomerDTO> result = customerService.updateCustomer(1L, updatedDTO);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("New Name");
        assertThat(result.get().email()).isEqualTo("new@example.com");
        assertThat(result.get().city()).isEqualTo("City2");
        assertThat(result.get().phoneNumber()).isEqualTo("+36702222222");
        assertThat(result.get().address()).isEqualTo("New Address");

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_CustomerNotExists()
    {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.updateCustomer(1L,
                new CustomerDTO("Test", "test@example.com", 25,
                        "City1", "+36703333333", "Test Address", true)
        );

        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
