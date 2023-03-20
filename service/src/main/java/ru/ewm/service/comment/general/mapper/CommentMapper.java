package ru.ewm.service.comment.general.mapper;

import ru.ewm.service.comment.general.dto.CreateUpdateCommentDto;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.model.Comment;
import ru.ewm.service.event.general.mapper.EventMapper;
import ru.ewm.service.user.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static Comment toComment(CreateUpdateCommentDto dto) {
        return Comment.builder()
                .text(dto.getText())
                .build();
    }

    public static FullCommentDto toFullCommentDto(Comment comment) {
        return FullCommentDto.builder()
                .id(comment.getId())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .text(comment.getText())
                .build();
    }

    public static List<FullCommentDto> toFullCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toFullCommentDto)
                .collect(Collectors.toList());
    }
}
