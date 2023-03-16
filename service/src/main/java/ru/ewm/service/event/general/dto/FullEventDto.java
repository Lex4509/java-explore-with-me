package ru.ewm.service.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ewm.service.category.general.dto.CategoryDto;
import ru.ewm.service.user.dto.ShortUserDto;
import ru.ewm.service.util.enums.State;
import ru.ewm.service.event.general.model.Location;

import java.time.LocalDateTime;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullEventDto {

    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Long id;
    private ShortUserDto initiator;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private long views;

    @Override
    public String toString() {
        return "FullEventDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
