package ru.ewm.service.compilation.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.compilation.general.dto.NewCompilationDto;
import ru.ewm.service.compilation.general.dto.UpdateCompilationRequest;
import ru.ewm.service.compilation.admin.service.AdminCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public ResponseEntity<Object> addCompilation(@RequestBody @NotNull @Valid NewCompilationDto newCompilationDto) {
        log.info("Create compilation {}", newCompilationDto);
        return new ResponseEntity<>(adminCompilationService.addCompilation(newCompilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Delete compilation {}", compId);
        adminCompilationService.deleteCompilation(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<Object> updateCompilation(@PathVariable(name = "compId") Long compId,
                                                    @RequestBody @NotNull UpdateCompilationRequest updateRequest) {
        log.info("Update compilation {}", compId);
        return new ResponseEntity<>(adminCompilationService
                .updateCompilation(compId, updateRequest), HttpStatus.OK);
    }
}
