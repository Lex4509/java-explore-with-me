package ru.ewm.service.event.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ewm.service.category.general.model.Category;
import ru.ewm.service.event.general.dto.CreateEventDto;
import ru.ewm.service.event.general.dto.FullEventDto;
import ru.ewm.service.event.general.dto.ShortEventDto;
import ru.ewm.service.event.general.model.Event;

import static ru.ewm.service.category.general.mapper.CategoryMapper.toCategoryDto;
import static ru.ewm.service.user.mapper.UserMapper.toUserShortDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEvent(CreateEventDto createEventDto) {
        Event event = new Event();
        event.setAnnotation(createEventDto.getAnnotation());
        Category category = new Category();
        event.setCategory(category);
        event.setDescription(createEventDto.getDescription());
        event.setEventDate(createEventDto.getEventDate());
        event.setLocation(createEventDto.getLocation());
        event.setPaid(createEventDto.isPaid());
        event.setParticipantLimit(createEventDto.getParticipantLimit());
        event.setRequestModeration(createEventDto.isRequestModeration());
        event.setTitle(createEventDto.getTitle());
        return event;
    }

    public static FullEventDto toEventFullDto(Event event) {
        FullEventDto fullEventDto = new FullEventDto();
        fullEventDto.setAnnotation(event.getAnnotation());
        fullEventDto.setCategory(toCategoryDto(event.getCategory()));
        fullEventDto.setCreatedOn(event.getCreatedOn());
        fullEventDto.setDescription(event.getDescription());
        fullEventDto.setEventDate(event.getEventDate());
        fullEventDto.setId(event.getId());
        fullEventDto.setInitiator(toUserShortDto(event.getInitiator()));
        fullEventDto.setLocation(event.getLocation());
        fullEventDto.setPaid(event.getPaid());
        fullEventDto.setParticipantLimit(event.getParticipantLimit());
        fullEventDto.setPublishedOn(event.getPublishedOn());
        fullEventDto.setRequestModeration(event.isRequestModeration());
        fullEventDto.setState(event.getState());
        fullEventDto.setTitle(event.getTitle());
        fullEventDto.setViews(0L);
        return fullEventDto;
    }

    public static ShortEventDto toEventShortDto(Event event) {
        ShortEventDto shortEventDto = new ShortEventDto();
        shortEventDto.setAnnotation(event.getAnnotation());
        shortEventDto.setCategory(toCategoryDto(event.getCategory()));
        shortEventDto.setEventDate(event.getEventDate());
        shortEventDto.setId(event.getId());
        shortEventDto.setInitiator(toUserShortDto(event.getInitiator()));
        shortEventDto.setPaid(event.getPaid());
        shortEventDto.setTitle(event.getTitle());
        return shortEventDto;
    }
}
