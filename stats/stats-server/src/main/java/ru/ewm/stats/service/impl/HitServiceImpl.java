package ru.ewm.stats.service.impl;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ewm.stats.model.EndpointHit;
import ru.ewm.stats.repository.EndpointHitRepository;
import ru.ewm.stats.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.ewm.stats.mapper.EndpointHitMapper.toEndpointHit;
import static ru.ewm.stats.mapper.EndpointHitMapper.toEndpointHitDto;

@Service
public class HitServiceImpl implements HitService {

    private final EndpointHitRepository repository;

    @Autowired
    public HitServiceImpl(EndpointHitRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public EndpointHitDto saveNewHit(EndpointHitDto hitDto) {
        EndpointHit hit = toEndpointHit(hitDto);
        return toEndpointHitDto(repository.save(hit));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return repository.getStatsUniqueIps(start, end, uris);
        }
        return repository.getStats(start, end, uris);
    }
}
