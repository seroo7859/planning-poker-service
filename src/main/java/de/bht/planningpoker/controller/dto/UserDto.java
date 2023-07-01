package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "User")
@Data
@Builder
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Name must be lower or upper case letter from a to z and can have white spaces")
    @Schema(name = "name", description = "The name of the user", example = "MyName")
    private String name;

    @Schema(name = "active", description = "Is user active", example = "true")
    private boolean active;

    @NotNull(message = "Role may not be null")
    @Schema(type = "Enum", name = "role", description = "The role of the user", example = "MODERATOR", allowableValues = { "MODERATOR", "PARTICIPANT", "SPECTATOR" })
    private String role;

}
