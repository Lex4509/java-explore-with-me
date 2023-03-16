package ru.ewm.service.participation_request.service;

import ru.ewm.service.participation_request.dto.RequestDto;
import ru.ewm.service.participation_request.dto.RequestStatusUpdateRequest;
import ru.ewm.service.participation_request.dto.RequestStatusUpdateResult;

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
