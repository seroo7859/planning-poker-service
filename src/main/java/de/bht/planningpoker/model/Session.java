package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "PublicId may not be blank")
    @UUID(message = "PublicId must be a UUID")
    @Column(nullable = false, updatable = false, length = 36, unique = true)
    private String publicId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team team;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deck_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Deck deck;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "backlog_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Backlog backlog;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        // Set missing back references
        if (Objects.isNull(team.getSession())) {
            team.setSession(this);
        }
        if (Objects.isNull(backlog.getSession())) {
            backlog.setSession(this);
        }
    }

}
