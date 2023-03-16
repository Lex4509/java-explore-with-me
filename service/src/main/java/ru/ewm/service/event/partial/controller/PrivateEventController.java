package ru.ewm.service.event.partial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.event.general.dto.CreateEventDto;
import ru.ewm.service.event.general.dto.UpdateEventUserRequest;
import ru.ewm.service.event.partial.service.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    @PostMapping
    public ResponseEntity<Object> addEvent(@PathVariable Long userId,
                                           @RequestBody @NotNull @Valid CreateEventDto createEventDto) {
        log.info("Create event {} by user {}", createEventDto, userId);
        return new ResponseEntity<>(privateEventService.addEvent(userId, createEventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero long from,
            @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        log.info("Get events by user {} from {}, page size {}", userId, from, size);
        return new ResponseEntity<>(privateEventService.getUserEvents(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Object> getUserEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Get event {} by user {}", eventId, userId);
        return new ResponseEntity<>(privateEventService.getUserEvent(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(
            @PathVariable Long userId, @PathVariable Long eventId,
            @RequestBody @NotNull @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("Update event {} by user {}", eventId, userId);
        return new ResponseEntity<>(privateEventService
                .updateEvent(userId, eventId, updateEventUserRequest), HttpStatus.OK);
    }
}
