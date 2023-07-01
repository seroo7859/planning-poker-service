package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "EstimationGive")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimationGiveDto {

    @NotBlank(message = "EstimationValue may not be blank")
    @Size(min = 1, max = 3, message = "EstimationValue must be between 1 and 3 characters long")
    @Schema(name = "estimationValue", description = "The estimation of the backlog item", example = "3")
    private String estimationValue;

}
