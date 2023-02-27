package ru.ewm.stats.repository;

import dto.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ewm.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select h.app, h.uri, count(h.ip) as ehits from EndpointHit as h " +
            "where h.timestamp > ?1 " +
            "and h.timestamp < ?2 " +
            "and h.uri in ?3 " +
            "group by h.app, h.uri " +
            "order by ehits desc")
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select h.app, h.uri, count(h.ip) as ehits from EndpointHit as h " +
            "where h.timestamp > ?1 " +
            "and h.timestamp < ?2 " +
            "and h.uri in ?3 " +
            "group by h.app, h.uri " +
            "order by ehits desc")
    List<ViewStatsDto> getStatsUniqueIps(LocalDateTime start, LocalDateTime end, List<String> uris);
}
