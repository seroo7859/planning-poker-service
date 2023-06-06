package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Schema(name = "Team")
@Data
@Builder
@AllArgsConstructor
public class TeamDto {

    @JsonIgnore
    public static final int MAX_TEAM_SIZE = 8;

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "name", description = "The name of team", example = "MyTeam")
    private String name;

    @NotNull(message = "Members may not be null")
    @Size(min = 1, max = MAX_TEAM_SIZE, message = "A maximum of " + MAX_TEAM_SIZE + " members can be in a team")
    @UniqueElements(message = "Members must not contain duplicates")
    @ArraySchema(arraySchema = @Schema(name = "members", description = "Set of team members"), uniqueItems = true)
    private List<@Valid TeamMemberDto> members;

    @Schema(name = "TeamMember", description = "A team member")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TeamMemberDto {

        @NotBlank(message = "Name may not be blank")
        @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Name must be lower or upper case letter from a to z and can have white spaces")
        @Schema(name = "name", description = "The name of team member", example = "John")
        private String name;

        @NotNull(message = "Active may not be null")
        @Schema(name = "active", description = "Is team member active", example = "true")
        private boolean active;

        @NotBlank(message = "Role may not be blank")
        @Schema(type = "Enum", name = "role", description = "The role of the team member", example = "MODERATOR", allowableValues = { "MODERATOR", "PARTICIPANT", "SPECTATOR" })
        private String role;

    }

}
