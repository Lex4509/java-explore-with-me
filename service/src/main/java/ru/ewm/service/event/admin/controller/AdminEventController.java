package ru.ewm.service.event.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.event.general.dto.UpdateEventAdminRequest;
import ru.ewm.service.event.admin.service.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public ResponseEntity<Object> findEvents(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<State> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero long from,
            @RequestParam(name = "size", defaultValue = "10") @Positive int size
    ) {
        log.info("Find event by users {}, states {}, categories {}, rangeStart {}, rangeEnd {}",
                users, states, categories, rangeStart, rangeEnd);
        return new ResponseEntity<>(adminEventService
                .adminEventSearch(users, states, categories, rangeStart, rangeEnd, from, size), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> updateEvent(
            @PathVariable Long eventId,
            @RequestBody @NotNull @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Update event {}", eventId);
        return new ResponseEntity<>(adminEventService.updateEvent(eventId, updateEventAdminRequest), HttpStatus.OK);
    }
}
