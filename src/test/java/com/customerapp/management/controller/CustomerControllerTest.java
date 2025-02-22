package com.customerapp.management.controller;

import com.customerapp.management.entity.Customer;
import com.customerapp.management.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.json.JSONObject;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "password";

    @Test
    public void testGetCustomers_Unauthorized() throws Exception
    {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void testGetCustomers_Authorized() throws Exception
    {
        registerUser();
        String token = obtainToken();

        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("testcustomer@example.com");
        customer.setAge(30);
        customer.setCity("Budapest");
        customer.setPhoneNumber("+36701234567");
        customer.setAddress("Example Street 12, Budapest");

        customerRepository.save(customer);

        mockMvc.perform(get("/api/customers")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private void registerUser() throws Exception
    {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAuthJson()))
                .andExpect(status().isOk());
    }

    private String obtainToken() throws Exception
    {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(createAuthJson()))
                                    .andReturn();

        return extractToken(result.getResponse().getContentAsString());
    }

    private String createAuthJson()
    {
        return """
        {
            "username": "%s",
            "password": "%s"
        }
        """.formatted(TEST_USERNAME, TEST_PASSWORD);
    }

    private String extractToken(String responseBody)
    {
        return Optional.ofNullable(responseBody)
                .map(body -> {
                    try
                    {
                        return new JSONObject(body).getString("token");
                    } catch (JSONException e) {
                        throw new RuntimeException("Failed to parse token from response: " + body, e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Token extraction failed. Response: " + responseBody));
    }
}


