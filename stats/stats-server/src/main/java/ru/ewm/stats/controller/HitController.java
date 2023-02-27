package ru.ewm.stats.controller;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.stats.service.HitService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Validated
public class HitController {
    private final HitService hitService;

    @Autowired
    public HitController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> saveNewHit(@RequestBody @Valid EndpointHitDto hitDto) {
        log.info("Save new endpoint hit = {}", hitDto);
        EndpointHitDto savedHit = hitService.saveNewHit(hitDto);
        return new ResponseEntity<>(savedHit, HttpStatus.CREATED);
    }

    @GetMapping("stats")
    public ResponseEntity<Object> getStats(@RequestParam(name = "start") String start,
                                           @RequestParam(name = "end") String end,
                                           @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                                           @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Get stats from = {} to = {} about uris = {} where unique ids = {}",
                start, end, uris, unique);
        List<ViewStatsDto> stats = hitService.getStats(start, end, uris, unique);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
