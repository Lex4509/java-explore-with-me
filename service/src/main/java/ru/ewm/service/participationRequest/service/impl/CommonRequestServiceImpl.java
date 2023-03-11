package ru.ewm.service.participationRequest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ewm.service.util.enums.RequestState;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participationRequest.model.ParticipationRequest;
import ru.ewm.service.participationRequest.repository.RequestRepository;
import ru.ewm.service.participationRequest.service.CommonRequestService;

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
