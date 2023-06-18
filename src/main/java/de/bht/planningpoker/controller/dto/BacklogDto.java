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

@Schema(name = "Backlog")
@Data
@Builder
@AllArgsConstructor
public class BacklogDto {

    @JsonIgnore
    public static final int MAX_BACKLOG_SIZE = 1000;

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "name", description = "The name of the backlog", example = "MyBacklog")
    private String name;

    @NotNull(message = "Items may not be null")
    @Size(min = 0, max = MAX_BACKLOG_SIZE, message = "A maximum of " + MAX_BACKLOG_SIZE + " backlog items can be in a backlog")
    @UniqueElements(message = "Items must not contain duplicates")
    @ArraySchema(arraySchema = @Schema(name = "items", description = "Set of backlog items"), uniqueItems = true)
    private List<@Valid BacklogItemDto> items;

    @Schema(name = "BacklogItem", description = "A backlog item")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BacklogItemDto {

        @JsonIgnore
        public static final String NUMBER_PREFIX = "US";

        @NotNull(message = "Number may not be null")
        @Size(min = 5, max = 5, message = "Number must be 5 characters long")
        @Pattern(regexp = "^" + NUMBER_PREFIX + "[0-9]{3}$", message = "Number must have the prefix " + NUMBER_PREFIX + " and continue with 3 digit characters")
        @Schema(name = "number", description = "The number of the backlog item", example = "US001")
        private String number;

        @NotBlank(message = "Title may not be blank")
        @Size(min = 1, max = 64, message = "Title must be between 1 and 64 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,64}$", message = "Title must be alphanumeric characters and can have white spaces, underscores and hyphens")
        @Schema(name = "title", description = "The title of the backlog item", example = "List Items")
        private String title;

        @NotNull(message = "Description may not be null")
        @Size(min = 0, max = 256, message = "Description must be between 0 and 256 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]{0,256}$", message = "Description must be alphanumeric characters and can have white spaces and punctuations")
        @Schema(name = "description", description = "The description of the backlog item", example = "As an authorized User I want to see the list of items so that I can select one")
        private String description;

        @NotNull(message = "Estimation may not be null")
        @Size(min = 0, max = 3, message = "Estimation must be between 0 and 3 characters long")
        @Pattern(regexp = "^[A-Za-z0-9]{0,3}$", message = "Estimation must be alphanumeric characters")
        @Schema(name = "estimation", description = "The estimation of the backlog item", example = "3")
        private String estimation;

        @NotNull(message = "Priority may not be null")
        @Size(min = 0, max = 32, message = "Priority must be between 0 and 32 characters long")
        @Pattern(regexp = "^[A-Za-z0-9 \\-]{0,32}$", message = "Priority must be alphanumeric characters and can have white spaces and hyphens")
        @Schema(name = "priority", description = "The priority of the backlog item", example = "5")
        private String priority;

    }

}
