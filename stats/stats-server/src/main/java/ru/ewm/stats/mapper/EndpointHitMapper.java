package ru.ewm.stats.mapper;

import dto.EndpointHitDto;
import lombok.NoArgsConstructor;
import ru.ewm.stats.model.EndpointHit;

@NoArgsConstructor
public class EndpointHitMapper {

    public static EndpointHit toEndpointHit(EndpointHitDto hitDto) {
        return new EndpointHit(
                hitDto.getId(),
                hitDto.getApp(),
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp()
        );
    }

    public static EndpointHitDto toEndpointHitDto(EndpointHit hit) {
        return new EndpointHitDto(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}
