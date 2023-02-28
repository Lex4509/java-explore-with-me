package ru.ewm.stats.repository;

import dto.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ewm.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;


public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
//    @Query("SELECT new dto.ViewStatsDto(e.app, e.uri, COUNT(distinct e.ip)) " +
//            " FROM EndpointHit AS e" +
//            " WHERE e.timestamp >= :start" +
//            " AND e.timestamp <= :end" +
//            " AND e.uri IN :uris" +
//            " GROUP BY e.app, e.uri")
    @Query(nativeQuery = true, name = "ViewStatsDtos")
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

//    @Query("SELECT new dto.ViewStatsDto(e.app, e.uri, COUNT(distinct e.ip)) " +
//            " FROM EndpointHit AS e" +
//            " WHERE e.timestamp >= :start" +
//            " AND e.timestamp <= :end" +
//            " AND e.uri IN :uris" +
//            " GROUP BY e.app, e.uri")
    @Query(nativeQuery = true, name = "ViewStatsDtosUniqueIps")
    List<ViewStatsDto> getStatsUniqueIps(LocalDateTime start, LocalDateTime end, List<String> uris);
}
