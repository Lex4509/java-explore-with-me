package ru.ewm.service.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.user.dto.CreateUserDto;
import ru.ewm.service.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody @NotNull @Valid CreateUserDto userDto) {
        log.info("Create user id {}", userDto);
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        log.info("Delete user id {}", userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers(@RequestParam(name = "ids", defaultValue = "") List<Long> ids,
                                           @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero long from,
                                           @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        if (ids.isEmpty()) {
            log.info("Get users from id {}, page size {}", from, size);
            return new ResponseEntity<>(userService.getAllUsers(from, size), HttpStatus.OK);
        }
        log.info("Get users ids from {}", ids);
        return new ResponseEntity<>(userService.getUsers(ids), HttpStatus.OK);
    }
}
