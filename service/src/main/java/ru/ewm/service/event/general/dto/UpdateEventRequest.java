package ru.ewm.service.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ewm.service.util.enums.StateAction;
import ru.ewm.service.event.general.model.Location;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class UpdateEventRequest {
    @Size(min = 20, max = 2000, message = "Invalid annotation")
    private String annotation;
    @Positive(message = "Invalid category")
    private Long category;
    @Size(min = 20, max = 7000, message = "Invalid description")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3, max = 120, message = "Invalid title")
    private String title;

    @Override
    public String toString() {
        return "CreateEventDto{" +
                "annotation='" + annotation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
