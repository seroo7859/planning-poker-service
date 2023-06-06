package de.bht.planningpoker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    SESSION_READ("session:read"),
    SESSION_CREATE("session:create"),
    SESSION_DELETE("session:delete"),
    SESSION_JOIN("session:join"),
    SESSION_LEAVE("session:leave");

    @NotBlank(message = "Permission may not be blank")
    @Pattern(regexp = "^[a-z]+:[a-z]+$", message = "Permission must be lower case letter from a to z and match with format <entity>:<operation>")
    @Getter
    private final String permission;
}
