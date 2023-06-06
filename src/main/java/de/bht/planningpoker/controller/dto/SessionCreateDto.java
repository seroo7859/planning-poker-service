package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "SessionCreate", description = "The data to create a session")
public class SessionCreateDto {

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "team", description = "The name of the team", example = "MyTeam")
    private String team;

    @NotBlank(message = "Username may not be blank")
    @Size(min = 1, max = 32, message = "Username must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Username must be lower or upper case letter from a to z and can have white spaces")
    @Schema(name = "username", description = "The name of the user that moderate the session", example = "MyName")
    private String username;

    @NotNull(message = "Deck may not be null")
    @Schema(name = "deck", description = "the deck that use for the estimates")
    private @Valid DeckDto deck;

}
