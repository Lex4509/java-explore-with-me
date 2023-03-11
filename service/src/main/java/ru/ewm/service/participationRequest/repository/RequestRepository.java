package ru.ewm.service.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ewm.service.util.enums.RequestState;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.participationRequest.model.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Optional<ParticipationRequest> findFirstByEventIdIsAndRequesterIdIs(Long eventId, Long requesterId);

    List<ParticipationRequest> findAllByRequesterIdIs(Long requesterId);

    List<ParticipationRequest> findAllByEventIdIs(Long eventId);

    List<ParticipationRequest> findAllByEventInAndStatusIs(List<Event> event, RequestState status);

    List<ParticipationRequest> findAllByIdIn(List<Long> ids);
}
