package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.UserDto;
import net.javaguides.__backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLastname(), // use instance method getId()
                user.getFirstname(),
                user.getEmail(),
                user.getAge()

        );
    }

    public User mapToUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getLastname(),
                userDto.getFirstname(),
                userDto.getEmail(),
                userDto.getAge()
        );
    }
}
