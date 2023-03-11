package ru.ewm.service.compilation.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.compilation.general.dto.CompilationDto;
import ru.ewm.service.compilation.general.dto.NewCompilationDto;
import ru.ewm.service.compilation.general.dto.UpdateCompilationRequest;
import ru.ewm.service.compilation.general.model.Compilation;
import ru.ewm.service.compilation.general.repository.CompilationRepository;
import ru.ewm.service.compilation.admin.service.AdminCompilationService;
import ru.ewm.service.event.general.dto.ShortEventDto;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.participationRequest.model.ParticipationRequest;
import ru.ewm.service.participationRequest.service.CommonRequestService;
import ru.ewm.service.util.validator.EntityValidator;

import java.util.List;
import java.util.Map;

import static ru.ewm.service.compilation.general.mapper.CompilationMapper.toCompilation;
import static ru.ewm.service.compilation.general.mapper.CompilationMapper.toCompilationDto;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CommonEventService commonEventService;
    private final CommonRequestService commonRequestService;
    private final EntityValidator entityValidator;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilationToSave = toCompilation(newCompilationDto);
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        compilationToSave.setEvents(events);
        CompilationDto compilationDto = toCompilationDto(compilationRepository.save(compilationToSave));

        Map<Long, Long> views = commonEventService.getStats(events, false);
        if (!views.isEmpty()) {
            compilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService.findConfirmedRequests(events);
        for (ShortEventDto shortDto : compilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }

        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        entityValidator.checkIfCompilationExist(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest) {
        Compilation compilationToUpdate = entityValidator.checkIfCompilationExist(compId);

        if (updateRequest.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(updateRequest.getEvents());
            compilationToUpdate.setEvents(events);
        }

        if (updateRequest.getPinned() != null) {
            compilationToUpdate.setPinned(updateRequest.getPinned());
        }

        if (updateRequest.getTitle() != null) {
            compilationToUpdate.setTitle(updateRequest.getTitle());
        }

        CompilationDto updatedCompilationDto = toCompilationDto(compilationRepository.save(compilationToUpdate));

        Map<Long, Long> views = commonEventService.getStats(compilationToUpdate.getEvents(), false);
        if (!views.isEmpty()) {
            updatedCompilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService
                .findConfirmedRequests(compilationToUpdate.getEvents());
        for (ShortEventDto shortDto : updatedCompilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }

        return updatedCompilationDto;
    }
}
