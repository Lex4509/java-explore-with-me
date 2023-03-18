package ru.ewm.service.comment.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ewm.service.comment.admin.service.AdminCommentService;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.mapper.CommentMapper;
import ru.ewm.service.comment.general.model.Comment;
import ru.ewm.service.comment.general.repository.CommentRepository;
import ru.ewm.service.comment.general.service.CommentService;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.user.repository.UserRepository;
import ru.ewm.service.util.enums.SortOrder;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentService commentService;

    @Override
    public Collection<FullCommentDto> getByUserId(long userId, SortOrder sortOrder, int from, int size) {
        userRepository.getReferenceById(userId);
        if (sortOrder.equals(SortOrder.ASC)) {
            return CommentMapper.toFullCommentDtoCollection(
                    commentRepository.findAllByAuthorIdOrderByCreatedOnAsc(userId, PageRequest.of(from, size))
            );
        }
        return CommentMapper.toFullCommentDtoCollection(
                commentRepository.findAllByAuthorIdOrderByCreatedOnDesc(userId, PageRequest.of(from, size))
        );
    }

    @Override
    public Collection<Comment> getByUserIdEventId(long userId, long eventId, SortOrder sortOrder, int from, int size) {
        userRepository.getReferenceById(userId);
        eventRepository.getReferenceById(eventId);

        if (sortOrder.equals(SortOrder.ASC)) {
            return commentRepository
                    .findAllByAuthorIdAndEventIdOrderByCreatedOnAsc(userId, eventId, PageRequest.of(from, size));
        }
        return commentRepository
                .findAllByAuthorIdAndEventIdOrderByCreatedOnDesc(userId, eventId, PageRequest.of(from, size));
    }

    @Override
    public void deleteByIdForAdmin(long commentId) {
        commentRepository.delete(commentService.getById(commentId));
    }

}
