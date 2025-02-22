package com.customerapp.management.controller;

import com.customerapp.management.entity.UserEntity;
import com.customerapp.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.json.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "password";

    @Test
    public void testRegisterUser() throws Exception
    {
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAuthJson()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = TEST_USERNAME)
    public void testLoginUser() throws Exception
    {
        userRepository.save(createTestUser());

        String token = obtainToken();

        mockMvc.perform(get("/api/customers")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private UserEntity createTestUser()
    {
        UserEntity user = new UserEntity();
        user.setUsername(TEST_USERNAME);
        user.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        return user;
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

    private String extractToken(String responseBody) throws JSONException
    {
        return new JSONObject(responseBody).getString("token");
    }
}
