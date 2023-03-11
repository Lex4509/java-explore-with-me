package ru.ewm.service.compilation.common.service;

import ru.ewm.service.compilation.general.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    CompilationDto getCompilation(Long compId);

    List<CompilationDto> findCompilations(boolean pinned, long from, int size);
}
