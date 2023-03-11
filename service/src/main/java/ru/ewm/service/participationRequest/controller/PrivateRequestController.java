package ru.ewm.service.participationRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.participationRequest.dto.RequestStatusUpdateRequest;
import ru.ewm.service.participationRequest.service.PrivateRequestService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/users/{userId}")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PrivateRequestController {

    private final PrivateRequestService privateRequestService;

    @PostMapping("/requests")
    public ResponseEntity<Object> addRequest(@PathVariable Long userId,
                                             @RequestParam(name = "eventId") Long eventId) {
        log.info("Create request user {} to event {}",
                userId, eventId);
        return new ResponseEntity<>(privateRequestService.addRequest(userId, eventId), HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public ResponseEntity<Object> findAllUsersRequests(@PathVariable Long userId) {
        log.info("Find all requests by user {}", userId);
        return new ResponseEntity<>(privateRequestService.findAllUsersRequests(userId), HttpStatus.OK);
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequest(@PathVariable Long userId,
                                                @PathVariable Long requestId) {
        log.info("Cancel request {} by user {}", requestId, userId);
        return new ResponseEntity<>(privateRequestService.cancelRequest(userId, requestId), HttpStatus.OK);
    }

    @GetMapping("/events/{eventId}/requests")
    public ResponseEntity<Object> getEventRequests(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        log.info("Get all requests from event {} by initiator {}", eventId, userId);
        return new ResponseEntity<>(privateRequestService.getEventRequests(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/events/{eventId}/requests")
    public ResponseEntity<Object> updateRequests(@PathVariable Long userId,
                                                 @PathVariable Long eventId,
                                                 @RequestBody @NotNull @Valid RequestStatusUpdateRequest request) {
        log.info("Update requests {} to status {}", request.getRequestIds(), request.getStatus());
        return new ResponseEntity<>(privateRequestService
                .updateRequests(userId, eventId, request), HttpStatus.OK);
    }


}
