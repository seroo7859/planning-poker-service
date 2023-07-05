package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;
import java.util.List;

@Schema(name = "SessionPage")
@Data
@Builder
@AllArgsConstructor
public class SessionPageDto {

    @NotNull(message = "Info may not be null")
    @Schema(name = "info", description = "Info of the session page")
    private @Valid SessionPageInfoDto info;

    @NotNull(message = "Items may not be null")
    @Schema(name = "items", description = "List of session page items")
    private List<@Valid SessionPageItemDto> items;

    @Schema(name = "SessionPageInfo", description = "Info of the session page")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SessionPageInfoDto {

        @PositiveOrZero(message = "PageNumber must be greater than or equal to 0")
        @Schema(name = "pageNumber", description = "The page number of session page", example = "1")
        private int pageNumber;

        @PositiveOrZero(message = "PageSize must be greater than or equal to 0")
        @Schema(name = "pageSize", description = "The page size of session page", example = "10")
        private int pageSize;

        @PositiveOrZero(message = "Offset must be greater than or equal to 0")
        @Schema(name = "offset", description = "The offset of session page", example = "0")
        private long offset;

        @PositiveOrZero(message = "TotalPages must be greater than or equal to 0")
        @Schema(name = "TotalPage", description = "The total number of session pages", example = "100")
        private int totalPages;

        @PositiveOrZero(message = "TotalItems must be greater than or equal to 0")
        @Schema(name = "TotalItems", description = "The total number of session page items", example = "1000")
        private long totalItems;

        @PositiveOrZero(message = "NumberOfItems must be greater than or equal to 0")
        @Schema(name = "NumberOfItems", description = "The number of session page items", example = "10")
        private int numberOfItems;

    }

    @Schema(name = "SessionPageItem", description = "The session page item of the session page")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SessionPageItemDto {

        @JsonIgnore
        public static final int MAX_TEAM_SIZE = 8;

        @JsonIgnore
        public static final int MAX_BACKLOG_SIZE = 1000;

        @JsonIgnore
        public static final int MAX_DISCUSSION_POST_COUNT = 10000;

        @NotBlank(message = "ID may not be blank")
        @UUID(message = "ID must be a UUID")
        @Schema(name = "id", description = "The public ID of the session", example = "0ea43720-2256-46de-8854-0359ef5c4977")
        private String id;

        @NotBlank(message = "TeamName may not be blank")
        @Size(min = 1, max = 32, message = "TeamName must be between 1 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "TeamName must be alphanumeric characters and can have white spaces, underscores and hyphens")
        @Schema(name = "teamName", description = "The name of team", example = "MyTeam")
        private String teamName;

        @Min(value = 1, message = "Minimum of 1 members can be in a team")
        @Max(value = MAX_TEAM_SIZE, message = "Maximum of " + MAX_TEAM_SIZE + " members can be in a team")
        @Schema(name = "totalMembers", description = "The total count of members in the team", example = "6")
        private int totalMembers;

        @Min(value = 0, message = "Minimum of 0 members can be active")
        @Max(value = MAX_TEAM_SIZE, message = "Maximum of " + MAX_TEAM_SIZE + " members can be active")
        @Schema(name = "activeMembers", description = "The count of active members", example = "3")
        private int activeMembers;

        @Min(value = 0, message = "Minimum of 0 backlog items can be in a backlog")
        @Max(value = MAX_BACKLOG_SIZE, message = "Maximum of " + MAX_BACKLOG_SIZE + " backlog items can be in a backlog")
        @Schema(name = "totalBacklogItems", description = "The total count of backlog items in the backlog", example = "10")
        private int totalBacklogItems;

        @Min(value = 0, message = "Minimum of 0 backlog items can be estimated")
        @Max(value = MAX_BACKLOG_SIZE, message = "Maximum of " + MAX_BACKLOG_SIZE + " backlog items can be estimated")
        @Schema(name = "estimatedBacklogItems", description = "The total count of estimated backlog items", example = "3")
        private int estimatedBacklogItems;

        @Min(value = 0, message = "Minimum of 0 discussion posts can be in a discussion")
        @Max(value = MAX_DISCUSSION_POST_COUNT, message = "Maximum of " + MAX_DISCUSSION_POST_COUNT + " discussion posts can be in a discussion")
        @Schema(name = "totalDiscussionPosts", description = "The total count of discussion posts", example = "36")
        private int totalDiscussionPosts;

        @PositiveOrZero(message = "TotalEstimationRounds must be greater than or equal to 0")
        @Schema(name = "totalEstimationRounds", description = "The total count of estimation rounds", example = "12")
        private int totalEstimationRounds;

        @PositiveOrZero(message = "TotalEstimations must be greater than or equal to 0")
        @Schema(name = "totalEstimations", description = "The total count of estimations", example = "22")
        private int totalEstimations;

        @NotBlank(message = "DeckName may not be blank")
        @Size(min = 1, max = 32, message = "DeckName must be between 1 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "DeckName must be alphanumeric characters and can have white spaces, underscores and hyphens")
        @Schema(name = "deckName", description = "The name of the deck", example = "Fibonacci")
        private String deckName;

        @NotBlank(message = "DeckCards may not be blank")
        @Pattern(regexp = "^\\s*[^,]{1,3}\\s*(\\s*,\\s*[^,]{0,3}\\s*)*$", message = "DeckCards must be a comma separated unique estimation values")
        @Schema(name = "deckCards", description = "The cards of the deck", example = "1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ?, ∞, ☕")
        private String deckCards;

        @NotNull(message = "CreatedAt may not be null")
        @PastOrPresent(message = "CreatedAp must be on past or present")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(name = "createdAt", description = "The creation date of the session", type = "string", example = "2023-05-16 10:00:00")
        private Date createdAt;

    }

}
