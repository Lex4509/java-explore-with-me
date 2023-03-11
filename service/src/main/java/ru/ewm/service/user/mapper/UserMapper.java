package ru.ewm.service.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ewm.service.user.dto.FullUserDto;
import ru.ewm.service.user.dto.CreateUserDto;
import ru.ewm.service.user.dto.ShortUserDto;
import ru.ewm.service.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User toUser(CreateUserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static FullUserDto toUserDto(User user) {
        return new FullUserDto(user.getEmail(), user.getId(), user.getName());
    }

    public static ShortUserDto toUserShortDto(User user) {
        return new ShortUserDto(user.getId(), user.getName());
    }
}
