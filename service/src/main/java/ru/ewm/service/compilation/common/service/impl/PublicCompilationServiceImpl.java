package ru.ewm.service.compilation.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.service.compilation.general.dto.CompilationDto;
import ru.ewm.service.compilation.general.mapper.CompilationMapper;
import ru.ewm.service.compilation.general.model.Compilation;
import ru.ewm.service.compilation.general.repository.CompilationRepository;
import ru.ewm.service.compilation.common.service.PublicCompilationService;
import ru.ewm.service.event.general.dto.ShortEventDto;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.event.general.service.CommonEventService;
import ru.ewm.service.participationRequest.model.ParticipationRequest;
import ru.ewm.service.participationRequest.service.CommonRequestService;
import ru.ewm.service.util.validator.EntityValidator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.ewm.service.compilation.general.mapper.CompilationMapper.toCompilationDto;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final EntityValidator entityValidator;
    private final CommonEventService commonEventService;
    private final CommonRequestService commonRequestService;

    @Override
    public CompilationDto getCompilation(Long compId) {
        Compilation foundCompilation = entityValidator.checkIfCompilationExist(compId);
        CompilationDto compilationDto = toCompilationDto(foundCompilation);

        Map<Long, Long> views = commonEventService.getStats(foundCompilation.getEvents(), false);
        if (!views.isEmpty()) {
            setViewsToDto(views, compilationDto);
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService
                .findConfirmedRequests(foundCompilation.getEvents());
        for (ShortEventDto shortDto : compilationDto.getEvents()) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }
        return compilationDto;
    }

    @Override
    public List<CompilationDto> findCompilations(boolean pinned, long from, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);

        List<Compilation> foundCompilations = compilationRepository
                .findAllByIdIsGreaterThanEqualAndPinnedIs(from, pinned, pageRequest);

        List<CompilationDto> compilationDtos = foundCompilations.stream()
                .map(CompilationMapper::toCompilationDto).collect(Collectors.toList());

        Set<Event> events = foundCompilations.stream()
                .map(Compilation::getEvents)
                .flatMap(List<Event>::stream)
                .collect(Collectors.toSet());

        Map<Long, Long> views = commonEventService.getStats(List.copyOf(events), false);
        if (!views.isEmpty()) {
            compilationDtos.forEach(compilationDto -> setViewsToDto(views, compilationDto));
        }

        List<ParticipationRequest> confirmedRequests = commonRequestService
                .findConfirmedRequests(List.copyOf(events));

        compilationDtos
                .forEach(compilationDto -> setConfirmedRequestsToDto(compilationDto.getEvents(), confirmedRequests));

        return compilationDtos;
    }

    private void setViewsToDto(Map<Long, Long> views, CompilationDto compilationDto) {
        compilationDto.getEvents().forEach(e -> e.setViews(views.get(e.getId())));
    }

    private void setConfirmedRequestsToDto(List<ShortEventDto> events, List<ParticipationRequest> confirmedRequests) {
        for (ShortEventDto shortDto : events) {
            shortDto.setConfirmedRequests((int) confirmedRequests.stream()
                    .filter(request -> request.getEvent().getId().equals(shortDto.getId()))
                    .count());
        }
    }
}
