package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "BacklogItemMove")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacklogItemMoveDto {

    @PositiveOrZero(message = "NewIndex must be greater than or equal to 0")
    @Schema(name = "newIndex", description = "The new index of the backlog item", example = "1")
    private int newIndex;

}
