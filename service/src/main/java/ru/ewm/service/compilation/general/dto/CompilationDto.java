package ru.ewm.service.compilation.general.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ewm.service.event.general.dto.ShortEventDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<ShortEventDto> events;
    private Long id;
    private boolean pinned;
    private String title;
}
