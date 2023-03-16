package ru.ewm.service.user.service;

import ru.ewm.service.user.dto.CreateUserDto;
import ru.ewm.service.user.dto.FullUserDto;

import java.util.List;

public interface UserService {
    FullUserDto addUser(CreateUserDto userDto);

    void deleteUser(Long userId);

    List<FullUserDto> getUsers(List<Long> ids);

    List<FullUserDto> getAllUsers(long from, int size);
}
