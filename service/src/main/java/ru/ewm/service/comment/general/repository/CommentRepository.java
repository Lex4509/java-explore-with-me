package ru.ewm.service.comment.general.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ewm.service.comment.general.model.Comment;

import java.sql.Timestamp;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventIdOrderByCreatedOnAsc(long eventId, PageRequest pageRequest);

    List<Comment> findAllByEventIdOrderByCreatedOnDesc(long eventId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdOrderByCreatedOnAsc(long userId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdOrderByCreatedOnDesc(long userId, PageRequest pageRequest);

    List<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnAsc(long userId, long eventId,
                                                                       PageRequest pageRequest);

    List<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnDesc(long userId, long eventId,
                                                                        PageRequest pageRequest);

    @Query(
            "FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn ASC")
    List<Comment> findByParamsAsc(String text, Timestamp startDate, Timestamp endDate);

    @Query(
            "FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn DESC")
    List<Comment> findByParamsDesc(String text, Timestamp startDate, Timestamp endDate);

}
