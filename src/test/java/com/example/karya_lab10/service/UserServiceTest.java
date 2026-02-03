package com.example.karya_lab10.service;

import com.example.karya_lab10.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldSaveUser() {

        userService.createUser(
                "testuser",
                "test@mail.com",
                "plain123"
        );

        verify(userRepository).save(any());
    }
}
