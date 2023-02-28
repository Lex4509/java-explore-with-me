package ru.ewm.stats.service.impl;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.stats.util.exception.ValidationException;
import ru.ewm.stats.model.EndpointHit;
import ru.ewm.stats.repository.EndpointHitRepository;
import ru.ewm.stats.service.HitService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.ewm.stats.mapper.EndpointHitMapper.toEndpointHit;
import static ru.ewm.stats.mapper.EndpointHitMapper.toEndpointHitDto;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    private final EndpointHitRepository repository;
    static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);

    @Transactional
    @Override
    public EndpointHitDto saveNewHit(EndpointHitDto hitDto) {
        EndpointHit hit = toEndpointHit(hitDto);
        return toEndpointHitDto(repository.save(hit));
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {

        LocalDateTime startDateTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, dateTimeFormatter);
        if (startDateTime.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Start date might not be in the future");
        }
        if (unique) {
            return repository.getStatsUniqueIps(startDateTime, endDateTime, uris)
                    .stream()
                    .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                    .collect(Collectors.toList());
        }
        return repository.getStats(startDateTime, endDateTime, uris)
                .stream()
                .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                .collect(Collectors.toList());
    }
}
