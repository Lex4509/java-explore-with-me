package ru.ewm.service.participationRequest.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ewm.service.participationRequest.dto.RequestDto;
import ru.ewm.service.participationRequest.model.ParticipationRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static RequestDto toParticipationRequestDto(ParticipationRequest request) {
        return new RequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
