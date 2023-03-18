package ru.ewm.service.comment.admin.service;

import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.model.Comment;
import ru.ewm.service.util.enums.SortOrder;

import java.util.Collection;

public interface AdminCommentService {

    Collection<FullCommentDto> getByUserId(long userId, SortOrder sort, int from, int size);

    Collection<Comment> getByUserIdEventId(long userId, long eventId, SortOrder sort, int from, int size);

    void deleteByIdForAdmin(long commentId);

}
