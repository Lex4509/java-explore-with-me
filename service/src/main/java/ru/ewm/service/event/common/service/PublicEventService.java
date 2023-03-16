package ru.ewm.service.event.common.service;

import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.util.enums.SortTypes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    FullEventDto getEvent(Long id, HttpServletRequest request);

    List<FullEventDto> publicEventSearch(String text,
                                         List<Long> categories,
                                         Boolean paid,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Boolean onlyAvailable,
                                         SortTypes sort,
                                         long from,
                                         int size,
                                         String ip);
}
