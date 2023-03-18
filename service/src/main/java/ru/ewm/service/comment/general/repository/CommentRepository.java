package ru.ewm.service.comment.general.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ewm.service.comment.general.model.Comment;

import java.sql.Timestamp;
import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Collection<Comment> findAllByEventIdOrderByCreatedOnAsc(long eventId, PageRequest pageRequest);

    Collection<Comment> findAllByEventIdOrderByCreatedOnDesc(long eventId, PageRequest pageRequest);

    Collection<Comment> findAllByAuthorIdOrderByCreatedOnAsc(long userId, PageRequest pageRequest);

    Collection<Comment> findAllByAuthorIdOrderByCreatedOnDesc(long userId, PageRequest pageRequest);

    Collection<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnAsc(long userId, long eventId,
                                                                       PageRequest pageRequest);

    Collection<Comment> findAllByAuthorIdAndEventIdOrderByCreatedOnDesc(long userId, long eventId,
                                                                        PageRequest pageRequest);

    @Query(
            "FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn ASC")
    Collection<Comment> findByParamsAsc(String text, Timestamp startDate, Timestamp endDate);

    @Query(
            "FROM Comment as c " +
            "WHERE (c.createdOn between :startDate and :endDate) " +
            "AND lower(c.text) like %:text% " +
            "ORDER BY c.createdOn DESC")
    Collection<Comment> findByParamsDesc(String text, Timestamp startDate, Timestamp endDate);

}
