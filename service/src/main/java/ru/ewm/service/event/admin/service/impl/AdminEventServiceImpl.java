package ru.ewm.service.event.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.util.exception.InvalidOperationException;
import ru.ewm.service.event.general.dto.UpdateEventRequest;
import ru.ewm.service.event.general.mapper.EventMapper;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.event.admin.service.AdminEventService;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.util.validator.EntityValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.ewm.service.event.general.mapper.EventMapper.toEventFullDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final EntityValidator entityValidator;
    private final CommonEventService commonEventService;

    @Override
    public List<FullEventDto> adminEventSearch(List<Long> users,
                                               List<State> states,
                                               List<Long> categories,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               long from,
                                               int size) {
        List<Event> foundEvents = eventRepository.adminEventSearch(
                users, states, categories, rangeStart, rangeEnd, from, size);

        List<FullEventDto> fullEventDtos = foundEvents.stream()
                .map(EventMapper::toEventFullDto).collect(Collectors.toList());

        Map<Long, Long> views = commonEventService.getStats(foundEvents, false);
        if (!views.isEmpty()) {
            fullEventDtos.forEach(e -> e.setViews(views.get(e.getId())));
        }
        return fullEventDtos;
    }

    @Override
    public FullEventDto updateEvent(Long eventId, UpdateEventRequest updateEventAdminRequest) {
        Event eventToUpdate = entityValidator.checkIfEventExist(eventId);
        if (eventToUpdate.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new InvalidOperationException("Invalid event date");
        }
        if (!State.PENDING.equals(eventToUpdate.getState())) {
            throw new InvalidOperationException(
                    String.format("Invalid state: %s",
                            eventToUpdate.getState().name()));
        }
        Event updatedEvent = commonEventService.updateEvent(eventToUpdate, updateEventAdminRequest);
        switch (updateEventAdminRequest.getStateAction()) {
            case PUBLISH_EVENT:
                updatedEvent.setPublishedOn(LocalDateTime.now());
                updatedEvent.setState(State.PUBLISHED);
                break;
            case REJECT_EVENT:
                updatedEvent.setState(State.CANCELED);
                break;
            default:
                throw new InvalidOperationException("Invalid state");
        }
        return toEventFullDto(eventRepository.save(updatedEvent));
    }
}
