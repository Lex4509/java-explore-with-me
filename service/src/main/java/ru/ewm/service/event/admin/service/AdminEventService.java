package ru.ewm.service.event.admin.service;

import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.event.general.dto.UpdateEventRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<FullEventDto> adminEventSearch(List<Long> users,
                                        List<State> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        long from,
                                        int size);

    FullEventDto updateEvent(Long eventId, UpdateEventRequest updateEventAdminRequest);
}
