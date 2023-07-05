package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Discussion {

    @Transient
    public static final int MAX_DISCUSSION_POST_COUNT = 10000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Topic may not be blank")
    @Size(min = 1, max = 32, message = "Topic must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Topic must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Column(nullable = false, length = 32)
    private String topic;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "discussion")
    private Session session;

    @Size(min = 0, max = MAX_DISCUSSION_POST_COUNT, message = "A maximum of " + MAX_DISCUSSION_POST_COUNT + " discussion posts can be in a discussion")
    @OneToMany(mappedBy = "discussion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name="list_index")
    private List<@Valid DiscussionPost> posts = new ArrayList<>();

    @NotNull(message = "Active may not be null")
    @Column(nullable = false)
    private Boolean active;

    @PastOrPresent(message = "StartedAt must be in the past or in the present")
    @Column(nullable = true)
    private LocalDateTime startedAt;

    @PastOrPresent(message = "EndedAt must be in the past or in the present")
    @Column(nullable = true)
    private LocalDateTime endedAt;

    public void addPost(DiscussionPost post) {
        if (active && posts.size() < MAX_DISCUSSION_POST_COUNT) {
            posts.add(post);
            post.setDiscussion(this);
        }
    }

    public void removePost(DiscussionPost post) {
        posts.remove(post);
        post.setDiscussion(null);
    }

    public int getPostCount() {
        return posts.size();
    }

    public boolean isActive() {
        return active;
    }

    public boolean isStarted() {
        return (Objects.nonNull(startedAt) && Objects.isNull(endedAt) &&
                startedAt.isBefore(LocalDateTime.now()) || active);
    }

    public boolean isEnded() {
        return (Objects.nonNull(startedAt) && Objects.nonNull(endedAt) &&
                endedAt.isAfter(startedAt)) || !active;
    }

    public void start() {
        if (isStarted()) {
            return;
        }
        startedAt = LocalDateTime.now();
        endedAt = null;
        active = true;
    }

    public void end() {
        if (isEnded()) {
            return;
        }
        endedAt = LocalDateTime.now();
        active = false;
    }

    public Duration getDuration() {
        if (Objects.isNull(startedAt) && Objects.isNull(endedAt)) {
            return Duration.ZERO;
        }
        if (isStarted()) {
            return Duration.between(startedAt, LocalDateTime.now());
        }
        return Duration.between(startedAt, endedAt);
    }

    @PrePersist
    private void setMissingBackReferences() {
        posts.forEach(post -> {
            if (Objects.isNull(post.getDiscussion())) {
                post.setDiscussion(this);
            }
        });
    }

}
