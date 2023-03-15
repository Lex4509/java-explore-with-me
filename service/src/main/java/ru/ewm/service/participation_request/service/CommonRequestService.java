package ru.ewm.service.participation_request.service;

import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participation_request.model.ParticipationRequest;

import java.util.List;

public interface CommonRequestService {
    List<ParticipationRequest> findConfirmedRequests(List<Event> events);
}
