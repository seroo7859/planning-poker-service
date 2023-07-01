package de.bht.planningpoker.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EstimationRound {

    @Transient
    public static final int MAX_ESTIMATION_COUNT = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "BacklogItem may not be null")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backlog_item_id", referencedColumnName = "id")
    private BacklogItem backlogItem;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false, updatable = false)
    private Session session;

    @Size(min = 0, max = MAX_ESTIMATION_COUNT, message = "A maximum of " + MAX_ESTIMATION_COUNT + " estimations can be give for a backlog item")
    @OneToMany(mappedBy = "estimationRound", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name="list_index")
    private List<@Valid Estimation> estimations = new ArrayList<>();

    @PastOrPresent(message = "StartedAt must be in the past or in the present")
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime startedAt;

    @PastOrPresent(message = "FinishedAt must be in the past or in the present")
    @Column(nullable = true)
    private LocalDateTime finishedAt;

    public void addEstimation(Estimation estimation) {
        if (estimations.size() < MAX_ESTIMATION_COUNT) {
            estimations.add(estimation);
            estimation.setEstimationRound(this);
        }
    }

    public void removeEstimation(Estimation estimation) {
        estimations.remove(estimation);
        estimation.setEstimationRound(null);
    }

    public void clearEstimations() {
        estimations.clear();
    }

    public int getEstimationCount() {
        return estimations.size();
    }

    public boolean hasEstimations() {
        return !estimations.isEmpty();
    }

    public List<Estimation> getEstimationsFromEstimator(User user) {
        return estimations.stream()
                .filter(estimation -> user.getUsername().equalsIgnoreCase(estimation.getCreatedBy().getUsername()))
                .toList();
    }

    public List<User> getEstimators() {
        return estimations.stream()
                .map(Estimation::getCreatedBy)
                .distinct()
                .toList();
    }

    public boolean isConsensusReached() {
        boolean allEstimationsAreSame = estimations.stream()
                .map(Estimation::getEstimationValue)
                .distinct()
                .count() == 1;
        boolean allUsersEstimate = getEstimators().size() == session.getUsers().size();
        return allEstimationsAreSame && allUsersEstimate;
    }

    public boolean isStarted() {
        return Objects.nonNull(startedAt) && Objects.isNull(finishedAt) &&
                startedAt.isBefore(LocalDateTime.now());
    }

    public boolean isFinished() {
        return Objects.nonNull(startedAt) && Objects.nonNull(finishedAt) &&
                finishedAt.isAfter(startedAt);
    }

    public void start() {
        session.addEstimationRound(this);
    }

    public void finish() {
        finishedAt = LocalDateTime.now();
        if (isConsensusReached()) {
            String estimationValue = estimations.get(0).getEstimationValue();
            backlogItem.setEstimation(estimationValue);
        }
    }

    public EstimationSummary getSummary() {
        final Map<String, Set<User>> usersPerEstimationValue = estimations.stream()
                .collect(Collectors.groupingBy(Estimation::getEstimationValue, Collectors.mapping(Estimation::getCreatedBy, Collectors.toSet())));

        final Set<EstimationSummary.EstimationResult> estimationResults = usersPerEstimationValue.entrySet()
                .stream()
                .map(userPerEstimationValue -> new EstimationSummary.EstimationResult(userPerEstimationValue.getKey(), userPerEstimationValue.getValue()))
                .collect(Collectors.toSet());

        return EstimationSummary.builder()
                .backlogItem(backlogItem)
                .startedAt(startedAt)
                .finishedAt(finishedAt)
                .totalEstimators(session.getUsers().size())
                .numberOfEstimators(getEstimators().size())
                .consensusReached(isConsensusReached())
                .recommendation(isConsensusReached() ? estimations.get(0).getEstimationValue() : Strings.EMPTY)
                .estimationResults(estimationResults)
                .build();
    }

    @PrePersist
    private void setMissingBackReferences() {
        estimations.forEach(estimation -> {
            if (Objects.isNull(estimation.getEstimationRound())) {
                estimation.setEstimationRound(this);
            }
        });
    }

}
