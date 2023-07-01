package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Schema(name = "EstimationSummery")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstimationSummaryDto {

    @JsonIgnore
    public static final int MAX_ESTIMATORS = 8;

    @JsonIgnore
    public static final String NUMBER_PREFIX = "US";

    @NotBlank(message = "BacklogItemNumber may not be blank")
    @Size(min = 5, max = 5, message = "BacklogItemNumber must be 5 characters long")
    @Pattern(regexp = "^" + NUMBER_PREFIX + "[0-9]{3}$", message = "BacklogItemNumber must have the prefix " + NUMBER_PREFIX + " and continue with 3 digit characters")
    @Schema(name = "backlogItemNumber", description = "The number of the backlog item", example = "US001")
    private String backlogItemNumber;

    @NotNull(message = "StartedAt may not be null")
    @PastOrPresent(message = "StartedAt must be on past or present")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "startedAt", description = "The start date of the estimation round", type = "string", example = "2023-05-16 10:00:00")
    private LocalDateTime startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "finishedAt", description = "The finish date of the estimation round", type = "string", example = "2023-05-16 10:00:00")
    private LocalDateTime finishedAt;

    @NotBlank(message = "Duration may not be blank")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "Duration must be have the time format HH:MM:SS")
    @Schema(name = "duration", description = "The duration of the estimation round", example = "00:05:00")
    private String duration;

    @Min(value = 0, message = "Minimum of 0 estimators can be estimate a backlog item")
    @Max(value = MAX_ESTIMATORS, message = "Maximum of " + MAX_ESTIMATORS + " estimators can be estimate a backlog item")
    @Schema(name = "totalEstimators", description = "The total count of estimators in the estimation round", example = "8")
    private int totalEstimators;

    @Min(value = 0, message = "Minimum of 0 estimators can be estimate a backlog item")
    @Max(value = MAX_ESTIMATORS, message = "Maximum of " + MAX_ESTIMATORS + " estimators can be estimate a backlog item")
    @Schema(name = "numberOfEstimators", description = "The number of estimators that give an estimation in the estimation round", example = "6")
    private int numberOfEstimators;

    @Schema(name = "consensusReached", description = "Is a consensus reached and all estimations are the same", example = "true")
    private boolean consensusReached;

    @NotNull(message = "Recommendation may not be null")
    @Size(min = 0, max = 3, message = "Recommendation must be between 0 and 3 characters long")
    @Schema(name = "recommendation", description = "The recommended estimation value of the estimation round", example = "3")
    private String recommendation;

    @NotNull(message = "EstimationResults may not be null")
    @UniqueElements(message = "EstimationResults must not contain duplicates")
    @ArraySchema(arraySchema = @Schema(name = "EstimationResults", description = "Set of estimation results"), uniqueItems = true)
    private Set<@Valid EstimationResultDto> estimationResults;

    @Schema(name = "EstimationResult", description = "A estimation result")
    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EstimationResultDto {

        @NotBlank(message = "EstimationValue may not be blank")
        @Size(min = 1, max = 3, message = "EstimationValue must be between 1 and 3 characters long")
        @Schema(name = "estimationValue", description = "The estimation value of the backlog item", example = "3")
        private String estimationValue;

        @NotNull(message = "Estimators may not be null")
        @Size(min = 0, max = MAX_ESTIMATORS, message = "A maximum of " + MAX_ESTIMATORS + " estimators can have estimated")
        @UniqueElements(message = "Estimators must not contain duplicates")
        @ArraySchema(arraySchema = @Schema(name = "Estimators", description = "Set of estimators", example ="[\"Max\", \"John\", \"Noha\"]"), uniqueItems = true)
        Set<String> estimators;

    }

}
