package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.UserDto;
import net.javaguides.__backend.entity.User;
import net.javaguides.__backend.Mapper.UserMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.UserRepository;
import net.javaguides.__backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;  // Inject UserMapper

    @Override
    public UserDto createUser(UserDto userDto) {
        // Convert UserDto to User entity using injected UserMapper
        // Check if a user with the same email already exists
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            // If email exists, throw a custom exception
            throw new ResourceNotFoundException("Email Duplicate");
        }

        User user = userMapper.mapToUser(userDto);
        // Save the user entity
        User savedUser = userRepository.save(user);
        // Convert saved user back to DTO
        return userMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        // Try to find the user by ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            // Handle case where user is not found
            throw new ResourceNotFoundException("User with id " + userId + " does not exist");
        }
        User user = userOptional.get();
        return userMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        // Try to find the user by ID
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            // Handle case where user is not found
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }
        // Delete the found user
        userRepository.delete(userOptional.get());
    }

    @Override
    public UserDto updateUser(Long id, UserDto updatedUserDto) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (!existingUserOptional.isPresent()) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        Optional<User> userWithSameEmail = userRepository.findByEmail(updatedUserDto.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new ResourceNotFoundException("Email Duplicate");
        }

        User updatedUser = userMapper.mapToUser(updatedUserDto);
        updatedUser.setId(id);

        // Save the updated user
        User savedUser = userRepository.save(updatedUser);
        return userMapper.mapToUserDto(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        // Get all users from the repository
        List<User> users = userRepository.findAll();
        // Convert users to DTOs and return as a list
        return users.stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }
}
