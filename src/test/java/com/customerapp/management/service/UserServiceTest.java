package com.customerapp.management.service;

import com.customerapp.management.entity.UserEntity;
import com.customerapp.management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser()
    {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity createdUser = userService.createUser("testuser", "password");

        assertThat(createdUser.getUsername()).isEqualTo("testuser");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testFindByUsername_UserExists()
    {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<UserEntity> foundUser = userService.findByUsername("testuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testFindByUsername_UserDoesNotExist()
    {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<UserEntity> foundUser = userService.findByUsername("nonexistent");

        assertThat(foundUser).isEmpty();
    }
}
