package de.bht.planningpoker.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimationSummary {

    public static final int MAX_ESTIMATORS = 8;

    @NotNull(message = "BacklogItem may not be null")
    @Valid BacklogItem backlogItem;

    @NotNull(message = "StartedAt may not be null")
    @PastOrPresent(message = "StartedAt must be in the past or in the present")
    LocalDateTime startedAt;

    @NotNull(message = "FinishedAt may not be null")
    @PastOrPresent(message = "FinishedAt must be in the past or in the present")
    LocalDateTime finishedAt;

    @Min(value = 0, message = "Minimum of 0 estimators can be estimate a backlog item")
    @Max(value = MAX_ESTIMATORS, message = "Maximum of " + MAX_ESTIMATORS + " estimators can be estimate a backlog item")
    int totalEstimators;

    @Min(value = 0, message = "Minimum of 0 estimators can be estimate a backlog item")
    @Max(value = MAX_ESTIMATORS, message = "Maximum of " + MAX_ESTIMATORS + " estimators can be estimate a backlog item")
    int numberOfEstimators;

    boolean consensusReached;

    @NotBlank(message = "Recommendation may not be blank")
    @Size(min = 1, max = 3, message = "Recommendation must be between 1 and 3 characters long")
    String recommendation;

    @NotNull(message = "EstimationResults may not be null")
    Set<@Valid EstimationResult> estimationResults;

    public Duration getDuration() {
        if (Objects.isNull(startedAt) && Objects.isNull(finishedAt)) {
            return Duration.ZERO;
        }
        if (Objects.nonNull(startedAt) && Objects.isNull(finishedAt)) {
            return Duration.between(startedAt, LocalDateTime.now());
        }
        return Duration.between(startedAt, finishedAt);
    }

    @Value
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EstimationResult {

        @NotBlank(message = "EstimationValue may not be blank")
        @Size(min = 1, max = 3, message = "EstimationValue must be between 1 and 3 characters long")
        @Pattern(regexp = "^[A-Za-z0-9]{1,3}$", message = "EstimationValue must be alphanumeric characters")
        String estimationValue;

        @NotNull(message = "Estimators may not be null")
        @Size(min = 0, max = MAX_ESTIMATORS, message = "A maximum of " + MAX_ESTIMATORS + " estimators can have estimated")
        Set<User> estimators;

    }

}
