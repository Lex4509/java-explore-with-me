package ru.ewm.service.event.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import ru.ewm.service.event.general.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {
    @NotBlank(message = "Blank title")
    @Size(min = 3, max = 120, message = "Invalid title")
    private String title;@NotBlank(message = "Blank annotation")
    @NotBlank(message = "Blank description")
    @Size(min = 20, max = 7000, message = "Invalid description")
    private String description;@Size(min = 20, max = 2000, message = "Invalid annotation")
    private String annotation;
    @NotNull(message = "Blank category")
    @Positive(message = "Invalid category")
    private Long category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    @NotNull(message = "Blank location")
    private Location location;
    private boolean paid;
    private int participantLimit;
    @Value("true")
    private boolean requestModeration;


    @Override
    public String toString() {
        return "CreateEventDto{" +
                "annotation='" + annotation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
