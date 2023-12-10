package de.bht.planningpoker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    SESSION_CREATE("session:create"),
    SESSION_DELETE("session:delete"),
    SESSION_JOIN("session:join"),
    SESSION_LEAVE("session:leave"),
    BACKLOG_IMPORT("backlog:import"),
    BACKLOG_EXPORT("backlog:export"),
    BACKLOG_CLEAR("backlog:clear"),
    BACKLOG_RENAME("backlog:rename"),
    BACKLOG_READ("backlog:read"),
    BACKLOG_ITEM_ADD("backlog-item:add"),
    BACKLOG_ITEM_REMOVE("backlog-item:remove"),
    BACKLOG_ITEM_UPDATE("backlog-item:update"),
    ESTIMATION_ROUND_START("estimation-round:start"),
    ESTIMATION_ROUND_FINISH("estimation-round:finish"),
    ESTIMATION_GIVE("estimation:give"),
    ESTIMATION_SUMMARY_READ("estimation-summary:read"),
    DISCUSSION_START("discussion:start"),
    DISCUSSION_STOP("discussion:stop"),
    DISCUSSION_POST_CREATE("discussion-post:create"),
    DISCUSSION_POST_READ("discussion-post:read");

    @NotBlank(message = "Permission may not be blank")
    @Pattern(regexp = "^[a-z\\-]+:[a-z\\-]+$", message = "Permission must be lower case letter from a to z and match with format <entity>:<operation>")
    @Getter
    private final String permission;
}
