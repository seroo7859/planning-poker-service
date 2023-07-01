package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(name = "SessionJoin")
@Data
@Builder
@AllArgsConstructor
public class SessionJoinDto {

    @NotBlank(message = "Username may not be blank")
    @Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Username must be lower or upper case letter from a to z and can have white spaces")
    @Schema(name = "username", description = "The name of the user that participate or spectate the session", example = "MyName")
    private String username;

    @Schema(name = "spectator", description = "Spectate or participate the session", example = "false", defaultValue = "false")
    private boolean spectator = false;

}
