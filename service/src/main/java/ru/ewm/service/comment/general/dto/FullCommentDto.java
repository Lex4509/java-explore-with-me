package ru.ewm.service.comment.general.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ewm.service.event.general.dto.ShortEventDto;
import ru.ewm.service.user.dto.ShortUserDto;

import java.sql.Timestamp;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullCommentDto {
    private long id;
    private ShortUserDto author;
    private ShortEventDto event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private Timestamp createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private Timestamp updatedOn;
    private String text;
}
