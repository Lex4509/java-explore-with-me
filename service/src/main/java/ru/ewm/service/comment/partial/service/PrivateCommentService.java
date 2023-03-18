package ru.ewm.service.comment.partial.service;

import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.model.Comment;

public interface PrivateCommentService {

    Comment create(long userId, long eventId, Comment comment);

    FullCommentDto update(long userId, long commentId, Comment comment);

    void deleteById(long userId, long commentId);

}
