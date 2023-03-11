package ru.ewm.service.compilation.admin.service;

import ru.ewm.service.compilation.general.dto.CompilationDto;
import ru.ewm.service.compilation.general.dto.NewCompilationDto;
import ru.ewm.service.compilation.general.dto.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest);
}
