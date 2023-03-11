package ru.ewm.service.participationRequest.service;

import ru.ewm.service.participationRequest.dto.RequestDto;
import ru.ewm.service.participationRequest.dto.RequestStatusUpdateRequest;
import ru.ewm.service.participationRequest.dto.RequestStatusUpdateResult;

import java.util.List;

public interface PrivateRequestService {
    RequestDto addRequest(Long userId, Long eventId);

    List<RequestDto> findAllUsersRequests(Long userId);

    RequestDto cancelRequest(Long userId, Long requestId);

    List<RequestDto> getEventRequests(Long userId, Long eventId);

    RequestStatusUpdateResult updateRequests(Long userId,
                                             Long eventId,
                                             RequestStatusUpdateRequest request);
}
