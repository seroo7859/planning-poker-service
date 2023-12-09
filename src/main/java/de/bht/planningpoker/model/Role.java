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
    ANONYMOUS(
        Set.of(
            SESSION_CREATE,
            SESSION_JOIN
        )
    ),
    MODERATOR(
        Set.of(
            SESSION_LEAVE,
            SESSION_DELETE,
            BACKLOG_IMPORT,
            BACKLOG_EXPORT,
            BACKLOG_CLEAR,
            BACKLOG_RENAME,
            BACKLOG_READ,
            BACKLOG_ITEM_ADD,
            BACKLOG_ITEM_REMOVE,
            BACKLOG_ITEM_UPDATE,
            ESTIMATION_ROUND_START,
            ESTIMATION_ROUND_FINISH,
            ESTIMATION_GIVE,
            ESTIMATION_SUMMARY_READ,
            DISCUSSION_START,
            DISCUSSION_STOP,
            DISCUSSION_POST_CREATE,
            DISCUSSION_POST_READ
        )
    ),
    PARTICIPANT(
        Set.of(
            SESSION_LEAVE,
            BACKLOG_READ,
            ESTIMATION_GIVE,
            ESTIMATION_SUMMARY_READ,
            DISCUSSION_POST_CREATE,
            DISCUSSION_POST_READ
        )
    ),
    SPECTATOR(
        Set.of(
            SESSION_LEAVE,
            BACKLOG_READ,
            ESTIMATION_SUMMARY_READ,
            DISCUSSION_POST_READ
        )
    );

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
