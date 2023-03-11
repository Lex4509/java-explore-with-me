package ru.ewm.service.compilation.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.compilation.common.service.PublicCompilationService;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping("/{compId}")
    public ResponseEntity<Object> getCompilation(@PathVariable(name = "compId") Long compId) {
        log.info("Get compilation by id {}", compId);
        return new ResponseEntity<>(publicCompilationService.getCompilation(compId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findCompilations(@RequestParam(name = "pinned", required = false) boolean pinned,
                                                   @RequestParam(name = "from", defaultValue = "0") long from,
                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("Find compilation from {}, page size {}", from, size);
        return new ResponseEntity<>(publicCompilationService.findCompilations(pinned, from, size), HttpStatus.OK);
    }
}
