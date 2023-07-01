package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<@Valid EstimationRound> estimationRounds = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @CreatedBy
    private User createdBy;

    @PastOrPresent(message = "CreatedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public List<User> getUsers() {
        return team.getMembers();
    }

    public List<User> getActiveUsers() {
        return team.getActiveMembers();
    }

    public void addEstimationRound(EstimationRound estimationRound) {
        estimationRounds.add(estimationRound);
        estimationRound.setSession(this);
    }

    public void removeEstimationRound(EstimationRound estimationRound) {
        estimationRounds.remove(estimationRound);
        estimationRound.setSession(null);
    }

    public Optional<EstimationRound> getCurrentEstimationRound() {
        if (estimationRounds.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(estimationRounds.get(estimationRounds.size() - 1));
    }

    public int getEstimationRoundCount() {
        return estimationRounds.size();
    }

    public int getEstimationCount() {
        return estimationRounds.stream()
                .mapToInt(EstimationRound::getEstimationCount)
                .sum();
    }

    @PrePersist
    private void prePersist() {
        // Set missing back references
        if (Objects.isNull(team.getSession())) {
            team.setSession(this);
        }
        if (Objects.isNull(backlog.getSession())) {
            backlog.setSession(this);
        }
        if (Objects.nonNull(estimationRounds)) {
            estimationRounds.forEach(estimationRound -> {
                if (Objects.isNull(estimationRound.getSession())) {
                    estimationRound.setSession(this);
                }
            });
        }
    }

}
