package ru.ewm.service.event.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.util.enums.SortTypes;
import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.event.common.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicEventController {

    private final PublicEventService publicEventService;

    @GetMapping("{id}")
    public ResponseEntity<Object> getEvent(@PathVariable(name = "id") @Positive Long id,
                                           HttpServletRequest request) {
        log.info("Get event {}", id);
        return new ResponseEntity<>(publicEventService.getEvent(id, request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> search(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) SortTypes sort,
            @RequestParam(name = "from", defaultValue = "0")@PositiveOrZero long from,
            @RequestParam(name = "size", defaultValue = "10") @Positive int size,
            HttpServletRequest request
    ) {
        String ip = request.getRemoteAddr();
        log.info("Search text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, onlyAvailable {}, " +
                        "sort {}, from {}, pageSize {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        List<FullEventDto> found = publicEventService
                .publicEventSearch(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, ip);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }
}
