package com.customerapp.management.entity;

import com.customerapp.management.dto.CustomerDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "customers")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private int age;

    @NotBlank(message = "City is required")
    private String city;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    private String address;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date registrationDate;

    private boolean isActive;

    public Customer()
    {
        this.registrationDate = new Date();
    }

    public Customer(CustomerDTO dto)
    {
        this.name = dto.name();
        this.email = dto.email();
        this.age = dto.age();
        this.city = dto.city();
        this.phoneNumber = dto.phoneNumber();
        this.address = dto.address();
        this.isActive = dto.isActive();
        this.registrationDate = new Date();
    }

    public Customer(Long id, String name, String email, int age, String city, String phoneNumber, String address, boolean isActive, Date registrationDate)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
    }

    public CustomerDTO toDTO()
    {
        return new CustomerDTO(name, email, age, city, phoneNumber, address, isActive);
    }

    public void updateFromDTO(CustomerDTO dto)
    {
        this.name = dto.name();
        this.email = dto.email();
        this.age = dto.age();
        this.city = dto.city();
        this.phoneNumber = dto.phoneNumber();
        this.address = dto.address();
        this.isActive = dto.isActive();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Date getRegistrationDate()
    {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate)
    {
        this.registrationDate = registrationDate;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }
}
