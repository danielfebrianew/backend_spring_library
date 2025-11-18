package com.tutorial.spring_library.service;

import com.tutorial.spring_library.exception.ResourceNotFoundException;
import com.tutorial.spring_library.model.User;
import com.tutorial.spring_library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // --- CREATE ---
    public User registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        return userRepository.save(user);
    }

    // --- READ ---
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // --- UPDATE ---
    public void updateUserProfile(UUID id, User userUpdates) {
        User existingUser = getUserById(id);

        existingUser.setFullName(userUpdates.getFullName());
        existingUser.setEmail(userUpdates.getEmail());

        userRepository.update(existingUser);
    }

    // --- DELETE ---
    public void deleteUser(UUID id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}