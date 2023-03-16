package ru.ewm.service.event.partial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.event.general.dto.CreateEventDto;
import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.util.exception.NotFoundException;
import ru.ewm.service.util.exception.InvalidOperationException;
import ru.ewm.service.event.general.dto.UpdateEventRequest;
import ru.ewm.service.event.general.mapper.EventMapper;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.event.partial.service.PrivateEventService;
import ru.ewm.service.participation_request.model.ParticipationRequest;
import ru.ewm.service.participation_request.service.CommonRequestService;
import ru.ewm.service.user.model.User;
import ru.ewm.service.util.validator.EntityValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.ewm.service.event.general.mapper.EventMapper.toEvent;
import static ru.ewm.service.event.general.mapper.EventMapper.toEventFullDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final EntityValidator entityValidator;
    private final CommonEventService commonEventService;
    private final CommonRequestService commonRequestService;

    @Override
    public FullEventDto addEvent(Long userId, CreateEventDto createEventDto) {
        if (createEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InvalidOperationException("Invalid event date");
        }
        Event eventToSave = toEvent(createEventDto);
        Category category = entityValidator.checkIfCategoryExist(createEventDto.getCategory());
        eventToSave.setCategory(category);
        User initiator = entityValidator.checkIfUserExist(userId);
        eventToSave.setInitiator(initiator);
        eventToSave.setState(State.PENDING);
        eventToSave.setCreatedOn(LocalDateTime.now());
        return toEventFullDto(eventRepository.save(eventToSave));
    }

    @Override
    public List<FullEventDto> getUserEvents(Long userId, long from, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        List<Event> foundEvents = eventRepository
                .findAllByIdIsGreaterThanEqualAndInitiatorIdIs(from, userId, pageRequest);

        List<FullEventDto> fullEventDtos = foundEvents.stream()
                .map(EventMapper::toEventFullDto).collect(Collectors.toList());

        Map<Long, Long> views = commonEventService.getStats(foundEvents, false);
        if (!views.isEmpty()) {
            fullEventDtos.forEach(e -> e.setViews(views.get(e.getId())));
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService.findConfirmedRequests(foundEvents);
        for (FullEventDto fullDto : fullEventDtos) {
            fullDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(fullDto.getId()))
                    .count());
        }

        return fullEventDtos;
    }

    @Override
    public FullEventDto getUserEvent(Long userId, Long eventId) {
        Event eventToReturn = entityValidator.checkIfEventExist(eventId);
        if (!Objects.equals(eventToReturn.getInitiator().getId(), userId)) {
            throw new NotFoundException(eventId, Event.class.getSimpleName());
        }
        FullEventDto fullEventDto = toEventFullDto(eventToReturn);
        if (State.PUBLISHED.equals(fullEventDto.getState())) {
            Map<Long, Long> views = commonEventService.getStats(List.of(eventToReturn), false);
            fullEventDto.setViews(views.get(eventToReturn.getId()));

            List<ParticipationRequest> confirmedRequests = commonRequestService
                    .findConfirmedRequests(List.of(eventToReturn));
            fullEventDto.setConfirmedRequests(confirmedRequests.size());
        }
        return fullEventDto;
    }

    @Override
    public FullEventDto updateEvent(Long userId, Long eventId, UpdateEventRequest updateEventUserRequest) {
        Event eventToUpdate = entityValidator.checkIfEventExist(eventId);
        if (!Objects.equals(eventToUpdate.getInitiator().getId(), userId)) {
            throw new InvalidOperationException("Event could be updated only by initiator");
        }
        Event updatedEvent = commonEventService.updateEvent(eventToUpdate, updateEventUserRequest);
        switch (updateEventUserRequest.getStateAction()) {
            case SEND_TO_REVIEW:
                updatedEvent.setState(State.PENDING);
                break;
            case CANCEL_REVIEW:
                updatedEvent.setState(State.CANCELED);
                break;
            default:
                throw new InvalidOperationException("Invalid state");
        }
        return toEventFullDto(eventRepository.save(updatedEvent));
    }
}
