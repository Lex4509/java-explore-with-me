package ru.ewm.service.participationRequest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.participationRequest.dto.RequestDto;
import ru.ewm.service.participationRequest.dto.RequestStatusUpdateRequest;
import ru.ewm.service.util.enums.RequestState;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.util.exception.InvalidOperationException;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participationRequest.dto.RequestStatusUpdateResult;
import ru.ewm.service.participationRequest.mapper.RequestMapper;
import ru.ewm.service.participationRequest.model.ParticipationRequest;
import ru.ewm.service.participationRequest.repository.RequestRepository;
import ru.ewm.service.participationRequest.service.CommonRequestService;
import ru.ewm.service.participationRequest.service.PrivateRequestService;
import ru.ewm.service.user.model.User;
import ru.ewm.service.util.validator.EntityValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.ewm.service.participationRequest.mapper.RequestMapper.toParticipationRequestDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final RequestRepository requestRepository;
    private final EntityValidator entityValidator;
    private final CommonRequestService commonRequestService;

    @Override
    public RequestDto addRequest(Long userId, Long eventId) {
        User requester = entityValidator.checkIfUserExist(userId);
        Event event = entityValidator.checkIfEventExist(eventId);

        Optional<ParticipationRequest> optionalRequest = requestRepository
                .findFirstByEventIdIsAndRequesterIdIs(event.getId(), requester.getId());

        optionalRequest.ifPresent(r -> {
            throw new InvalidOperationException("Request already exist");
        });

        if (event.getInitiator().equals(requester)) {
            throw new InvalidOperationException("Request could not be created by event initiator");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new InvalidOperationException("Event is not published");
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService
                .findConfirmedRequests(List.of(event));
        if (confirmedRequests.size() == event.getParticipantLimit()) {
            throw new InvalidOperationException("Limit over");
        }

        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setCreated(LocalDateTime.now());
        participationRequest.setEvent(event);
        participationRequest.setRequester(requester);

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            participationRequest.setStatus(RequestState.CONFIRMED);
        } else {
            participationRequest.setStatus(RequestState.PENDING);
        }

        return toParticipationRequestDto(requestRepository.save(participationRequest));
    }

    @Override
    public List<RequestDto> findAllUsersRequests(Long userId) {
        List<ParticipationRequest> foundRequests = requestRepository.findAllByRequesterIdIs(userId);
        return foundRequests.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        User requester = entityValidator.checkIfUserExist(userId);
        ParticipationRequest request = entityValidator.checkIfRequestExist(requestId);
        if (!request.getRequester().equals(requester)) {
            throw new InvalidOperationException("You can not cancel this request");
        }
        request.setStatus(RequestState.CANCELED);
        return toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> getEventRequests(Long userId, Long eventId) {
        User initiator = entityValidator.checkIfUserExist(userId);
        Event event = entityValidator.checkIfEventExist(eventId);
        if (!event.getInitiator().equals(initiator)) {
            throw new InvalidOperationException("User is not event initiator");
        }
        List<ParticipationRequest> foundRequests = requestRepository.findAllByEventIdIs(eventId);
        return foundRequests.stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public RequestStatusUpdateResult updateRequests(Long userId,
                                                    Long eventId,
                                                    RequestStatusUpdateRequest request) {
        User initiator = entityValidator.checkIfUserExist(userId);
        Event event = entityValidator.checkIfEventExist(eventId);
        if (!event.getInitiator().equals(initiator)) {
            throw new InvalidOperationException("User is not event initiator");
        }

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            throw new InvalidOperationException("RequestS does not need confirmation");
        }

        int confirmedRequests = commonRequestService.findConfirmedRequests(List.of(event)).size();

        List<ParticipationRequest> requestsToUpdate = requestRepository.findAllByIdIn(request.getRequestIds());
        RequestStatusUpdateResult requestStatusUpdateResult = new RequestStatusUpdateResult();
        List<RequestDto> requestDtos;
        switch (request.getStatus()) {
            case REJECTED:
                for (ParticipationRequest r : requestsToUpdate) {

                    if (!r.getStatus().equals(RequestState.PENDING)) {
                        throw new InvalidOperationException("Request must be in PENDING");
                    }

                    r.setStatus(RequestState.REJECTED);
                }
                requestDtos = requestRepository.saveAll(requestsToUpdate)
                        .stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
                requestStatusUpdateResult.setRejectedRequests(requestDtos);
                break;
            case CONFIRMED:
                for (ParticipationRequest r : requestsToUpdate) {

                    if (!r.getStatus().equals(RequestState.PENDING)) {
                        throw new InvalidOperationException("Request must be in PENDING");
                    }

                    if (confirmedRequests == event.getParticipantLimit()) {
                        throw new InvalidOperationException("Limit is over");
                    }

                    r.setStatus(RequestState.CONFIRMED);
                    confirmedRequests++;
                }
                requestDtos = requestRepository.saveAll(requestsToUpdate)
                        .stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
                requestStatusUpdateResult.setConfirmedRequests(requestDtos);
                break;
        }
        return requestStatusUpdateResult;
    }
}
