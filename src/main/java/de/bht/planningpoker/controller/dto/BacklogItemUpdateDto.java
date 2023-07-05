package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "BacklogItemUpdate")
@Data
@Builder
@AllArgsConstructor
public class BacklogItemUpdateDto {

    @NotBlank(message = "Title may not be blank")
    @Size(min = 1, max = 64, message = "Title must be between 1 and 64 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,64}$", message = "Title must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "title", description = "The title of the backlog item", example = "List Items")
    private String title;

    @NotNull(message = "Description may not be null")
    @Size(min = 0, max = 256, message = "Description must be between 0 and 256 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{0,256}$", message = "Description must be alphanumeric characters and can have white spaces and punctuations")
    @Schema(name = "description", description = "The description of the backlog item", example = "As an authorized User I want to see the list of items so that I can select one")
    private String description;

    @NotNull(message = "Estimation may not be null")
    @Size(min = 0, max = 3, message = "Estimation must be between 0 and 3 characters long")
    @Pattern(regexp = "^[A-Za-z0-9½?∞\\p{So}]{0,3}$", message = "Estimation must be alphanumeric characters or other symbols")
    @Schema(name = "estimation", description = "The estimation of the backlog item", example = "3")
    private String estimation;

    @NotNull(message = "Priority may not be null")
    @Size(min = 0, max = 32, message = "Priority must be between 0 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 \\-]{0,32}$", message = "Priority must be alphanumeric characters and can have white spaces and hyphens")
    @Schema(name = "priority", description = "The priority of the backlog item", example = "5")
    private String priority;

}
