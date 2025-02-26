package com.customerapp.management.repository;

import com.customerapp.management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    @Query("SELECT AVG(c.age) FROM Customer c")
    Float findAverageAge();
}
