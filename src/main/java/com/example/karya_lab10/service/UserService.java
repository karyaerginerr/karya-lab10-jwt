package com.example.karya_lab10.service;

import com.example.karya_lab10.model.User;
import com.example.karya_lab10.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String email, String password) {

        if (username == null || username.length() < 3) {
            throw new IllegalArgumentException("Username too short");
        }

        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password too short");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword);
        return userRepository.save(user);
    }

    public boolean authenticate(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
