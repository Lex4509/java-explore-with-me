package ru.ewm.service.comment.partial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.mapper.CommentMapper;
import ru.ewm.service.comment.general.model.Comment;
import ru.ewm.service.comment.general.repository.CommentRepository;
import ru.ewm.service.comment.general.service.CommentService;
import ru.ewm.service.comment.partial.service.PrivateCommentService;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.user.model.User;
import ru.ewm.service.user.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentService commentService;

    @Override
    public Comment create(long userId, long eventId, Comment comment) {
        comment.setAuthor(userRepository.getReferenceById(userId));
        comment.setEvent(eventRepository.getReferenceById(eventId));
        comment.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return commentRepository.save(comment);
    }

    @Override
    public FullCommentDto update(long userId, long commentId, Comment comment) {
        User user = userRepository.getReferenceById(userId);
        Comment currentComment = commentService.getById(commentId);

        if (comment.getText() != null)
            currentComment.setText(comment.getText());
        currentComment.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

        return CommentMapper.toFullCommentDto(commentRepository.save(currentComment));
    }

    @Override
    public void deleteById(long userId, long commentId) {
        userRepository.getReferenceById(userId);
        commentRepository.delete(commentService.getById(commentId));
    }

}
