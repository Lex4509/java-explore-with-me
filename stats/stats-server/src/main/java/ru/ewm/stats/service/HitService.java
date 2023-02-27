package ru.ewm.stats.service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;

import java.util.List;

public interface HitService {
    EndpointHitDto saveNewHit(EndpointHitDto hitDto);

    List<ViewStatsDto> getStats(String start,
                                String end,
                                List<String> uris,
                                Boolean unique);
}
