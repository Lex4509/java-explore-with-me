package ru.ewm.service.comment.general.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ewm.service.comment.general.model.Comment;
import ru.ewm.service.comment.general.repository.CommentRepository;
import ru.ewm.service.comment.general.service.CommentService;
import ru.ewm.service.util.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment getById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(commentId, "Comment not found")
        );
    }

}
