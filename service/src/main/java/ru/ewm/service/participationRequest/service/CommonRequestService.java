package ru.ewm.service.participationRequest.service;

import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participationRequest.model.ParticipationRequest;

import java.util.List;

public interface CommonRequestService {
    List<ParticipationRequest> findConfirmedRequests(List<Event> events);
}
