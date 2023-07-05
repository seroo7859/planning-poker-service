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

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "Discussion")
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscussionDto {

    @JsonIgnore
    public static final int MAX_DISCUSSION_POST_COUNT = 10000;

    @NotBlank(message = "Topic may not be blank")
    @Size(min = 1, max = 32, message = "Topic must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Topic must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "topic", description = "The topic of the discussion", example = "General")
    private String topic;

    @Schema(name = "active", description = "Is discussion active", example = "true")
    private boolean active;

    @PastOrPresent(message = "StartedAt must be on past or present")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "startedAt", description = "The start date of the discussion", type = "string", example = "2023-05-16 10:00:00")
    private LocalDateTime startedAt;

    @PastOrPresent(message = "EndedAt must be on past or present")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "endedAt", description = "The end date of the discussion", type = "string", example = "2023-05-16 10:00:00")
    private LocalDateTime endedAt;

    @NotBlank(message = "Duration may not be blank")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$", message = "Duration must be have the time format HH:MM:SS")
    @Schema(name = "duration", description = "The duration of the estimation round", example = "00:03:00")
    private String duration;

    @NotNull(message = "Posts may not be null")
    @Size(min = 0, max = MAX_DISCUSSION_POST_COUNT, message = "A maximum of " + MAX_DISCUSSION_POST_COUNT + " discussion posts can be in a discussion")
    private List<@Valid DiscussionPostDto> posts;

    @Schema(name = "DiscussionPost", description = "A discussion post")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DiscussionPostDto {

        @NotBlank(message = "Content may not be blank")
        @Size(min = 1, max = 512, message = "Content must be between 1 and 512 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\r\\n\\p{So}]{0,256}$", message = "Content must be alphanumeric characters and can have white spaces, punctuations, linebreaks and other symbols")
        @Schema(name = "content", description = "The content of the discussion post", example = "Let's estimate together.")
        private String content;

        @NotBlank(message = "Author may not be blank")
        @Size(min = 1, max = 32, message = "Author must be between 1 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Author must be lower or upper case letter from a to z and can have white spaces")
        @Schema(name = "author", description = "The name of the author", example = "Max")
        private String author;

        @NotNull(message = "CreatedAt may not be null")
        @PastOrPresent(message = "CreatedAp must be on past or present")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(name = "createdAt", description = "The creation date of the discussion post", type = "string", example = "2023-05-16 10:00:00")
        private LocalDateTime createdAt;

        @NotNull(message = "UpdatedAt may not be null")
        @PastOrPresent(message = "UpdatedAt must be on past or present")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(name = "updatedAt", description = "The update date of the discussion post", type = "string", example = "2023-05-16 10:00:00")
        private LocalDateTime updatedAt;

    }

}
