package com.example.karya_lab10.service;

import com.example.karya_lab10.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUser() {
        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        userService.createUser(
                "testuser",
                "test@mail.com",
                "plain123"
        );

        verify(userRepository).save(any());
    }

    @Test
    void createUser_shouldEncodePassword() {
        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        userService.createUser(
                "testuser",
                "test@mail.com",
                "plain123"
        );

        verify(passwordEncoder).encode("plain123");
    }

    @Test
    void createUser_withShortUsername_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(
                    "ab",
                    "test@mail.com",
                    "plain123"
            );
        });
    }
}
