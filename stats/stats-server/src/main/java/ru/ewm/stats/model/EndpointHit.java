package ru.ewm.stats.model;

import dto.ViewStatsDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
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
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;

    public EndpointHit(Long id, String app, String uri, String ip, LocalDateTime timestamp) {
        this.id = id;
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }

    public EndpointHit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
