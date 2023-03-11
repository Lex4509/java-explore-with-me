package ru.ewm.service.event.general.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.util.exception.InvalidOperationException;
import ru.ewm.service.event.general.dto.UpdateEventRequest;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.util.validator.EntityValidator;
import ru.ewm.stats.client.web.HitsClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_FORMATTER;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonEventServiceImpl implements CommonEventService {

    private final EntityValidator entityValidator;
    private final HitsClient hitsClient;

    @Override
    public Event updateEvent(Event eventToUpdate, UpdateEventRequest updateEventRequest) {
        if (eventToUpdate.getState().equals(State.PUBLISHED)) {
            throw new InvalidOperationException("Invalid state");
        }
        if (updateEventRequest.getAnnotation() != null) {
            eventToUpdate.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            Category category = entityValidator.checkIfCategoryExist(updateEventRequest.getCategory());
            eventToUpdate.setCategory(category);
        }
        if (updateEventRequest.getDescription() != null) {
            eventToUpdate.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            if (updateEventRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new InvalidOperationException("Invalid date");
            }
            eventToUpdate.setEventDate(updateEventRequest.getEventDate());
        }
        if (updateEventRequest.getLocation() != null) {
            eventToUpdate.setLocation(updateEventRequest.getLocation());
        }
        if (updateEventRequest.getPaid() != null) {
            eventToUpdate.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getRequestModeration() != null) {
            eventToUpdate.setRequestModeration(updateEventRequest.getRequestModeration());
        }
        if (updateEventRequest.getTitle() != null) {
            eventToUpdate.setTitle(updateEventRequest.getTitle());
        }
        return eventToUpdate;
    }

    @Override
    public Map<Long, Long> getStats(List<Event> events,
                                    Boolean unique) {

        Optional<LocalDateTime> start = events.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull).min(LocalDateTime::compareTo);
        if (start.isEmpty()) {
            return new HashMap<>();
        }
        LocalDateTime timestamp = LocalDateTime.now();
        List<Long> ids = events.stream().map(Event::getId).collect(Collectors.toList());

        String startTime = start.get().format(DATE_TIME_FORMATTER);
        String endTime = timestamp.format(DATE_TIME_FORMATTER);
        List<String> uris = ids.stream().map(id -> "/events/" + id).collect(Collectors.toList());

        ResponseEntity<Object> response = hitsClient.getViewStats(startTime, endTime, uris, unique);
        List<ViewStatsDto> stats;
        ObjectMapper mapper = new ObjectMapper();
        try {
            stats = Arrays.asList(mapper.readValue(mapper.writeValueAsString(response.getBody()), ViewStatsDto[].class));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        Map<Long, Long> views = new HashMap<>();
        for (Long id : ids) {
            Optional<Long> viewsOptional = stats.stream()
                    .filter(s -> s.getUri().equals("/events/" + id)).map(ViewStatsDto::getHits).findFirst();
            Long eventViews = viewsOptional.orElse(0L);
            views.put(id, eventViews);
        }

        return views;
    }
}
