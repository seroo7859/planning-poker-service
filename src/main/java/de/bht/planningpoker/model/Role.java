package de.bht.planningpoker.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.bht.planningpoker.model.Permission.*;

@RequiredArgsConstructor
public enum Role {
    MODERATOR(Set.of(SESSION_CREATE, SESSION_READ, SESSION_DELETE, SESSION_JOIN, SESSION_LEAVE)),
    PARTICIPANT(Set.of(SESSION_READ, SESSION_JOIN, SESSION_LEAVE)),
    SPECTATOR(Set.of(SESSION_READ, SESSION_JOIN, SESSION_LEAVE));

    @NotNull(message = "permissions may not be null")
    @Getter
    private final Set<@Valid Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return authorities;
    }
}
