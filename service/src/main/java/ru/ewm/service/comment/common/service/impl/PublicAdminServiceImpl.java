package ru.ewm.service.comment.common.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ewm.service.comment.common.service.PublicCommentService;
import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.comment.general.mapper.CommentMapper;
import ru.ewm.service.comment.general.repository.CommentRepository;
import ru.ewm.service.event.general.repository.EventRepository;
import ru.ewm.service.util.enums.SortOrder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PublicAdminServiceImpl implements PublicCommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public Collection<FullCommentDto> getByEventId(long eventId, SortOrder sortOrder, int from, int size) {
        eventRepository.getReferenceById(eventId);
        if (sortOrder.equals(SortOrder.ASC)) {
            return CommentMapper.toFullCommentDtoCollection(
                    commentRepository.findAllByEventIdOrderByCreatedOnAsc(eventId, PageRequest.of(from, size))
            );
        }
        return CommentMapper.toFullCommentDtoCollection(
                commentRepository.findAllByEventIdOrderByCreatedOnDesc(eventId, PageRequest.of(from, size))
        );
    }

    @Override
    public Collection<FullCommentDto> getAllByParams(String text, Timestamp startDate, Timestamp endDate, SortOrder sortOrder) {
        if (startDate == null)
            startDate = Timestamp.valueOf(LocalDateTime.now().minusYears(2));
        if (endDate == null)
            endDate = Timestamp.valueOf(LocalDateTime.now());
        if (text != null) text = text.toLowerCase();

        if (sortOrder == null || sortOrder.equals(SortOrder.ASC)) {
            return CommentMapper.toFullCommentDtoCollection(commentRepository.findByParamsAsc(
                    text,
                    startDate,
                    endDate));
        } else {
            return CommentMapper.toFullCommentDtoCollection(commentRepository.findByParamsDesc(
                    text,
                    startDate,
                    endDate));
        }

    }

}
