package ru.ewm.service.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ewm.service.category.general.dto.CategoryDto;
import ru.ewm.service.user.dto.ShortUserDto;

import java.time.LocalDateTime;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortEventDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Long id;
    private ShortUserDto initiator;
    private Boolean paid;
    private String title;
    private long views;

    @Override
    public String toString() {
        return "ShortEventDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
