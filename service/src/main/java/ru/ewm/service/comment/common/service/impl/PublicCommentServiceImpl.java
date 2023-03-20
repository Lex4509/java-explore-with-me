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
import java.util.List;

import static ru.ewm.service.util.enums.Constants.COMMENT_START_DATE_OFFSET;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public List<FullCommentDto> getByEventId(long eventId, SortOrder sortOrder, int from, int size) {
        eventRepository.getReferenceById(eventId);
        if (SortOrder.ASC.equals(sortOrder)) {
            return CommentMapper.toFullCommentDtoList(
                    commentRepository.findAllByEventIdOrderByCreatedOnAsc(eventId, PageRequest.of(from, size))
            );
        }
        return CommentMapper.toFullCommentDtoList(
                commentRepository.findAllByEventIdOrderByCreatedOnDesc(eventId, PageRequest.of(from, size))
        );
    }

    @Override
    public List<FullCommentDto> getAllByParams(String text, Timestamp startDate, Timestamp endDate, SortOrder sortOrder) {
        if (startDate == null)
            startDate = Timestamp.valueOf(LocalDateTime.now().minusYears(COMMENT_START_DATE_OFFSET));
        if (endDate == null)
            endDate = Timestamp.valueOf(LocalDateTime.now());
        if (text != null) text = text.toLowerCase();

        if (sortOrder == null || SortOrder.ASC.equals(sortOrder)) {
            return CommentMapper.toFullCommentDtoList(commentRepository.findByParamsAsc(
                    text,
                    startDate,
                    endDate));
        } else {
            return CommentMapper.toFullCommentDtoList(commentRepository.findByParamsDesc(
                    text,
                    startDate,
                    endDate));
        }

    }

}
