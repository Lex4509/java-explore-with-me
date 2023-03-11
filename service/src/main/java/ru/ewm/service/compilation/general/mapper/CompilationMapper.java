package ru.ewm.service.compilation.general.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ewm.service.compilation.general.dto.CompilationDto;
import ru.ewm.service.compilation.general.dto.NewCompilationDto;
import ru.ewm.service.compilation.general.model.Compilation;
import ru.ewm.service.event.general.mapper.EventMapper;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.isPinned());
        return compilation;
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()),
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }
}
