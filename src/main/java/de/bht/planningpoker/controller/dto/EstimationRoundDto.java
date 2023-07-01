package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "EstimationRound")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstimationRoundDto {

    @Transient
    public static final int MAX_ESTIMATION_COUNT = 8;

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

    @PastOrPresent(message = "FinishedAt must be on past or present")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "finishedAt", description = "The finish date of the estimation round", type = "string", example = "2023-05-16 10:00:00")
    private LocalDateTime finishedAt;

    @NotNull(message = "Estimations may not be null")
    @Size(min = 0, max = MAX_ESTIMATION_COUNT, message = "A maximum of " + MAX_ESTIMATION_COUNT + " estimations can be give for a backlog item")
    private List<@Valid EstimationDto> estimations;

    @Schema(name = "Estimation", description = "A estimation")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EstimationDto {

        @NotBlank(message = "EstimationValue may not be blank")
        @Size(min = 1, max = 3, message = "EstimationValue must be between 1 and 3 characters long")
        @Schema(name = "estimationValue", description = "The estimation value of the backlog item", example = "3")
        private String estimationValue;

        @NotBlank(message = "Estimator may not be blank")
        @Size(min = 1, max = 32, message = "Estimator must be between 1 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Estimator must be lower or upper case letter from a to z and can have white spaces")
        @Schema(name = "estimator", description = "The name of the estimator", example = "Max")
        private String estimator;

        @NotNull(message = "CreatedAt may not be null")
        @PastOrPresent(message = "CreatedAp must be on past or present")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(name = "createdAt", description = "The creation date of the estimation", type = "string", example = "2023-05-16 10:00:00")
        private LocalDateTime createdAt;

        @NotNull(message = "UpdatedAt may not be null")
        @PastOrPresent(message = "UpdatedAt must be on past or present")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(name = "updatedAt", description = "The update date of the estimation", type = "string", example = "2023-05-16 10:00:00")
        private LocalDateTime updatedAt;

    }
}
