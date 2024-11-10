package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.UserDto;
import net.javaguides.__backend.entity.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long UserId);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    List<UserDto> getAllUsers();

}
