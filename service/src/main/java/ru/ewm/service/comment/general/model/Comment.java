package ru.ewm.service.comment.general.model;

import lombok.*;
import ru.ewm.service.event.general.model.Event;
import ru.ewm.service.user.model.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private Timestamp createdOn;

    private Timestamp updatedOn;

    @Column(nullable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(author, comment.author)
                && Objects.equals(event, comment.event)
                && Objects.equals(createdOn, comment.createdOn)
                && Objects.equals(updatedOn, comment.updatedOn)
                && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, event, createdOn, updatedOn, text);
    }
}
