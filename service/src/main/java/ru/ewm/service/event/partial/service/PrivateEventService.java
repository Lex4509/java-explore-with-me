package ru.ewm.service.event.partial.service;

import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.event.general.dto.CreateEventDto;
import ru.ewm.service.event.general.dto.UpdateEventRequest;

import java.util.List;

public interface PrivateEventService {
    FullEventDto addEvent(Long userId, CreateEventDto createEventDto);

    List<FullEventDto> getUserEvents(Long userId, long from, int size);

    FullEventDto getUserEvent(Long userId, Long eventId);

    FullEventDto updateEvent(Long userId, Long eventId, UpdateEventRequest updateEventUserRequest);
}
