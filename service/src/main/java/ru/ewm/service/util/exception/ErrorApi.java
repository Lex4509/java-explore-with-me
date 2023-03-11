package ru.ewm.service.util.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.ewm.service.util.enums.Constants.DATE_TIME_PATTERN;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorApi {
    private String status;
    private String reason;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}
