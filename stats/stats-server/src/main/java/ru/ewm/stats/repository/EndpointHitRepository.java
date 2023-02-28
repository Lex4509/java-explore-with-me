package ru.ewm.stats.repository;

import dto.ViewStatsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ewm.stats.model.EndpointHit;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;
import java.util.List;

@NamedNativeQuery(name = "ViewStatsDtos",
        query = "select h.app, h.uri, count(h.ip) as ehits from hits as h " +
                "where h.timestamp > ?1 " +
                "and h.timestamp < ?2 " +
                "and h.uri in ?3 " +
                "group by h.app, h.uri " +
                "order by ehits desc", resultSetMapping = "ViewStatsDtoMapping")
@NamedNativeQuery(name = "ViewStatsDtosUniqueIps",
        query = "select h.app, h.uri, count(distinct (h.ip)) as ehits from hits as h " +
                "where h.timestamp > ?1 " +
                "and h.timestamp < ?2 " +
                "and h.uri in ?3 " +
                "group by h.app, h.uri " +
                "order by ehits desc", resultSetMapping = "ViewStatsDtoMapping")
@SqlResultSetMapping(name = "ViewStatsDtoMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "app", type = String.class),
                                @ColumnResult(name = "uri", type = String.class),
                                @ColumnResult(name = "ehits", type = Long.class)
                        },
                        targetClass = ViewStatsDto.class
                )}
)
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
