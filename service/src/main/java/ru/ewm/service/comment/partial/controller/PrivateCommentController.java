package ru.ewm.service.comment.partial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ewm.service.comment.general.dto.CreateUpdateCommentDto;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.mapper.CommentMapper;
import ru.ewm.service.comment.partial.service.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/users/{userId}/comments")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentController {

    private final PrivateCommentService privateCommentService;

    @PostMapping("{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FullCommentDto create(@PathVariable @Min(0) long userId,
                                 @PathVariable @Min(0) long eventId,
                                 @RequestBody @Valid CreateUpdateCommentDto dto) {
        log.info("Create new comment for event {}", eventId);
        return CommentMapper.toFullCommentDto(
                privateCommentService.create(userId, eventId, CommentMapper.toComment(dto)));
    }

    @PatchMapping("{commentId}")
    public FullCommentDto update(@PathVariable @Min(0) long userId,
                                 @PathVariable @Min(0) long commentId,
                                 @RequestBody @Valid CreateUpdateCommentDto dto) {
        log.info("Update comment {}", commentId);
        return privateCommentService.update(userId, commentId, CommentMapper.toComment(dto));
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable @Min(0) long userId,
                                  @PathVariable @Min(0) long commentId) {
        log.info("Delete comment {}", commentId);
        privateCommentService.deleteById(userId, commentId);
    }

}
