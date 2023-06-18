package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "BacklogRename")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacklogRenameDto {

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "name", description = "The name of the backlog", example = "MyBacklog")
    private String name;

}
