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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId);  // Assuming you have a custom findById method
        if (user == null) {
            throw new ResourceNotFoundException("User with id " + userId + " does not exist");
        }
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        boolean deleted = userRepository.deleteUser(id); // Assuming deleteUser returns true if deleted
        if (!deleted) {
            throw new ResourceNotFoundException("User with id " + id + " could not be deleted");
        }
    }

    @Override
    public UserDto updateUser(Long id, UserDto updatedUserDto) {
        User existingUser = userRepository.findById(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }

        // Map updatedUserDto to User object and set fields in existingUser
        User updatedUser = UserMapper.mapToUser(updatedUserDto);

    //can maybe put saveUser
        User savedUser = userRepository.editUser(id, updatedUser);  // Assuming editUser saves and returns updated user
        return UserMapper.mapToUserDto(savedUser);
    }

    //potentially change according to video the part of .map
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();  // Assuming findAll() fetches all User records
        return users.stream()
                .map(UserMapper::mapToUserDto)  // Convert each User to UserDto
                .collect(Collectors.toList());
    }
}
