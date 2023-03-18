package ru.ewm.service.comment.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.comment.admin.service.AdminCommentService;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.mapper.CommentMapper;
import ru.ewm.service.comment.general.service.CommentService;
import ru.ewm.service.util.enums.SortOrder;

import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
@Slf4j
public class AdminCommentController {

    private final AdminCommentService adminCommentService;
    private final CommentService commentService;

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Min(0) long commentId) {
        log.info("Delete comment {}", commentId);
        adminCommentService.deleteByIdForAdmin(commentId);
    }

    @GetMapping("{commentId}")
    public FullCommentDto getById(
            @PathVariable @Min(0) long commentId) {
        log.info("GET comment {}", commentId);

        return CommentMapper.toFullCommentDto(commentService.getById(commentId));
    }

    @GetMapping("{userId}/users")
    public Collection<FullCommentDto> getAllByUserId(
            @PathVariable @Min(0) long userId,
            @RequestParam(defaultValue = "DESC") SortOrder sortOrder,
            @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
            @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("GET comments userId={} from={} size={}", userId, from, size);
        return adminCommentService.getByUserId(userId, sortOrder, from, size);
    }

    @GetMapping("{userId}/users/{eventId}/events")
    public Collection<FullCommentDto> getByUserIdEventId(
            @PathVariable @Min(0) long userId,
            @PathVariable @Min(0) long eventId,
            @RequestParam(defaultValue = "DESC") SortOrder sortOrder,
            @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
            @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        log.info("GET comments userId {} eventId {} from {} size {}", userId, eventId, from, size);
        return CommentMapper.toFullCommentDtoCollection(
                adminCommentService.getByUserIdEventId(userId, eventId, sortOrder, from, size));
    }

}
