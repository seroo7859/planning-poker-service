package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;

@Schema(name = "Session")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionDto {

    @NotBlank(message = "ID may not be blank")
    @UUID(message = "ID must be a UUID")
    @Schema(name = "id", description = "The public ID of the session", example = "0ea43720-2256-46de-8854-0359ef5c4977")
    private String id;

    @NotNull(message = "Team may not be null")
    private @Valid TeamDto team;

    @NotNull(message = "Deck may not be null")
    private @Valid DeckDto deck;

    @NotNull(message = "Backlog may not be null")
    private @Valid BacklogDto backlog;

    private @Valid EstimationRoundDto estimationRound;

    private @Valid EstimationSummaryDto estimationSummary;

    @NotNull(message = "CreatedAt may not be null")
    @PastOrPresent(message = "CreatedAp must be on past or present")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "createdAt", description = "The creation date of the session", type = "string", example = "2023-05-16 10:00:00")
    private Date createdAt;

}
