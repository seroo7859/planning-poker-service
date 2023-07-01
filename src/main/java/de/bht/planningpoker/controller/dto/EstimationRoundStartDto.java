package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "EstimationRoundStart")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimationRoundStartDto {

    @JsonIgnore
    public static final String NUMBER_PREFIX = "US";

    @NotBlank(message = "BacklogItemNumber may not be blank")
    @Size(min = 5, max = 5, message = "BacklogItemNumber must be 5 characters long")
    @Pattern(regexp = "^" + NUMBER_PREFIX + "[0-9]{3}$", message = "BacklogItemNumber must have the prefix " + NUMBER_PREFIX + " and continue with 3 digit characters")
    @Schema(name = "backlogItemNumber", description = "The number of the backlog item", example = "US001")
    private String backlogItemNumber;

}
