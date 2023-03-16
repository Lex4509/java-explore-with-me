package ru.ewm.service.participation_request.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ewm.service.util.enums.RequestState;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participation_request.model.ParticipationRequest;
import ru.ewm.service.participation_request.repository.RequestRepository;
import ru.ewm.service.participation_request.service.CommonRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonRequestServiceImpl implements CommonRequestService {

    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequest> findConfirmedRequests(List<Event> events) {
        return requestRepository
                .findAllByEventInAndStatusIs(events, RequestState.CONFIRMED);
    }
}
