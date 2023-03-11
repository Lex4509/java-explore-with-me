package ru.ewm.service.event.general.service;

import ru.ewm.service.event.general.dto.UpdateEventRequest;
import ru.ewm.service.event.general.model.Event;

import java.util.List;
import java.util.Map;

public interface CommonEventService {
    Event updateEvent(Event eventToUpdate, UpdateEventRequest updateEventRequest);

    Map<Long, Long> getStats(List<Event> events, Boolean unique);
}
