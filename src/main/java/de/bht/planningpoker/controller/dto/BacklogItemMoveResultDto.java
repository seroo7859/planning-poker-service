package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "BacklogItemMoveResult")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacklogItemMoveResultDto {

    @NotNull(message = "Backlog may not be null")
    private @Valid BacklogDto backlog;

    @NotNull(message = "BacklogItem may not be null")
    private @Valid BacklogDto.BacklogItemDto backlogItem;

    @PositiveOrZero(message = "NewIndex must be greater than or equal to 0")
    @Schema(name = "newIndex", description = "The new index of the backlog item", example = "1")
    private int newIndex;

}
