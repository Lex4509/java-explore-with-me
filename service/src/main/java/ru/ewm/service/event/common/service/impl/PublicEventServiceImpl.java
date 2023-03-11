package ru.ewm.service.event.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.util.enums.SortTypes;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.util.exception.NotFoundException;
import ru.ewm.service.event.general.mapper.EventMapper;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.event.common.service.PublicEventService;
import ru.ewm.service.participationRequest.model.ParticipationRequest;
import ru.ewm.service.participationRequest.service.CommonRequestService;
import ru.ewm.service.util.validator.EntityValidator;
import ru.ewm.stats.client.web.HitsClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.ewm.service.util.enums.Constants.APP_NAME;
import static ru.ewm.service.event.general.mapper.EventMapper.toEventFullDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final HitsClient hitsClient;
    private final EntityValidator entityValidator;
    private final CommonEventService commonEventService;
    private final CommonRequestService commonRequestService;

    @Override
    public FullEventDto getEvent(Long id, HttpServletRequest request) {
        Event eventToReturn = entityValidator.checkIfEventExist(id);
        if (!eventToReturn.getState().equals(State.PUBLISHED)) {
            throw new NotFoundException(id, Event.class.getSimpleName());
        }

        FullEventDto fullEventDto = toEventFullDto(eventToReturn);

        Map<Long, Long> views = commonEventService.getStats(List.of(eventToReturn), false);
        fullEventDto.setViews(views.get(eventToReturn.getId()));

        List<ParticipationRequest> confirmedRequests = commonRequestService
                .findConfirmedRequests(List.of(eventToReturn));
        fullEventDto.setConfirmedRequests(confirmedRequests.size());

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        hitsClient.saveNewHit(APP_NAME, uri, ip, LocalDateTime.now());
        return fullEventDto;
    }

    @Override
    public List<FullEventDto> publicEventSearch(String text,
                                                List<Long> categories,
                                                Boolean paid,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Boolean onlyAvailable,
                                                SortTypes sort,
                                                long from,
                                                int size,
                                                String ip) {

        List<Event> foundEvents = eventRepository.publicEventSearch(text, categories, paid, rangeStart, rangeEnd, from, size);
        if (foundEvents.isEmpty()) {
            return List.of();
        }

        List<FullEventDto> fullEventDtos = foundEvents.stream()
                .map(EventMapper::toEventFullDto).collect(Collectors.toList());

        Map<Long, Long> views = commonEventService.getStats(foundEvents, false);
        fullEventDtos.forEach(e -> e.setViews(views.get(e.getId())));

        List<ParticipationRequest> confirmedRequests = commonRequestService.findConfirmedRequests(foundEvents);
        for (FullEventDto fullDto : fullEventDtos) {
            fullDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(fullDto.getId()))
                    .count());
        }

        LocalDateTime timestamp = LocalDateTime.now();
        hitsClient.saveNewHit(APP_NAME, "/events", ip, timestamp);
        foundEvents.forEach(event -> hitsClient.saveNewHit(APP_NAME, "/events/" + event.getId(), ip, timestamp));

        if (sort != null && sort.equals(SortTypes.VIEWS)) {
            return fullEventDtos.stream()
                    .sorted(Comparator.comparing(FullEventDto::getViews)).collect(Collectors.toList());
        }
        return fullEventDtos.stream()
                .sorted(Comparator.comparing(FullEventDto::getEventDate)).collect(Collectors.toList());
    }
}
