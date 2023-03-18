package ru.ewm.service.comment.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.comment.common.service.PublicCommentService;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.util.enums.SortOrder;

import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Collection;

@RestController
@RequestMapping("/comments")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {

    private final PublicCommentService publicCommentService;

    @GetMapping("{eventId}")
    public Collection<FullCommentDto> getAllByEventId(
            @PathVariable @Min(0) long eventId,
            @RequestParam(defaultValue = "DESC") SortOrder sortOrder,
            @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
            @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("GET comments eventId {} from {} size {}", eventId, from, size);
        return publicCommentService.getByEventId(eventId, sortOrder, from, size);
    }

    @GetMapping
    public Collection<FullCommentDto> getFilteredComments(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Timestamp startDate,
            @RequestParam(required = false) Timestamp endDate,
            @RequestParam(required = false) SortOrder sortOrder) {
        log.info("GET comments by text {}, startDate {}, endDate {}, sort {}",
                text, startDate, endDate, sortOrder);

        return publicCommentService.getAllByParams(text, startDate,
                        endDate, sortOrder);
    }

}
