package ru.ewm.service.comment.common.service;

import ru.ewm.service.comment.general.dto.FullCommentDto;
import ru.ewm.service.util.enums.SortOrder;

import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Collection;

public interface PublicCommentService {

    Collection<FullCommentDto> getByEventId(long eventId, SortOrder sort, @Min(0) int from, @Min(1) int size);

    Collection<FullCommentDto> getAllByParams(String text, Timestamp startDate, Timestamp endDate, SortOrder sort);


}
