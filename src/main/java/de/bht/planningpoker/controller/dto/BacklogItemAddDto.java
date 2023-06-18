package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "BacklogItemAdd")
@Data
@Builder
@AllArgsConstructor
public class BacklogItemAddDto {

    @NotBlank(message = "Title may not be blank")
    @Size(min = 1, max = 64, message = "Title must be between 1 and 64 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,64}$", message = "Title must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "title", description = "The title of the backlog item", example = "List Items")
    private String title;

    @Size(min = 0, max = 256, message = "Description must be between 0 and 256 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{0,256}$", message = "Description must be alphanumeric characters and can have white spaces and punctuations")
    @Schema(name = "description", description = "The description of the backlog item", example = "As an authorized User I want to see the list of items so that I can select one")
    private String description;

}
