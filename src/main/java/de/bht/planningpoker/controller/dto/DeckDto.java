package de.bht.planningpoker.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@Schema(name = "Deck")
@Data
@Builder
@AllArgsConstructor
public class DeckDto {

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 _\\-]{1,32}$", message = "Name must be alphanumeric characters and can have white spaces, underscores and hyphens")
    @Schema(name = "name", description = "The name of the deck", example = "MyDeck")
    private String name;

    @UniqueElements(message = "Cards must not contain duplicates")
    @ArraySchema(arraySchema = @Schema(name = "cards", description = "Set of cards"), uniqueItems = true)
    private List<@Valid DeckCardDto> cards;

    @Schema(name = "DeckCard", description = "A card")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DeckCardDto {

        @NotBlank(message = "Value may not be blank")
        @Size(min = 1, max = 3, message = "Value must be between 1 and 3 characters long")
        @Schema(name = "value", description = "The estimation value", example = "1")
        private String value;

        @NotNull(message = "Colors may not be null")
        private @Valid DeckCardColorsDto colors;

    }

    @Schema(name = "DeckCardColors", description = "The colors of the card")
    @Data
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DeckCardColorsDto {

        @NotBlank(message = "FrontTextColor may not be blank")
        @Size(min = 3, max = 7, message = "FrontTextColor must be 3 or 6 characters long")
        @Pattern(regexp = "^#?([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "FrontTextColor must be hexadecimal color value and can start with hashtag")
        @Schema(name = "frontTextColor", description = "The front text color of the card", example = "#000000")
        private String frontTextColor;

        @NotBlank(message = "BackTextColor may not be blank")
        @Size(min = 3, max = 7, message = "BackTextColor must be 3 or 6 characters long")
        @Pattern(regexp = "^#?([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "BackTextColor must be hexadecimal color value and can start with hashtag")
        @Schema(name = "backTextColor", description = "The back text color of the card", example = "#DC3545")
        private String backTextColor;

        @NotBlank(message = "BackgroundColor may not be blank")
        @Size(min = 3, max = 7, message = "BackgroundColor must be 3 or 6 characters long")
        @Pattern(regexp = "^#?([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "BackgroundColor must be hexadecimal color value and can start with hashtag")
        @Schema(name = "backgroundColor", description = "The background color of the card", example = "#F8F9FA")
        private String backgroundColor;

        @NotBlank(message = "HoverColor may not be blank")
        @Size(min = 3, max = 7, message = "HoverColor must be 3 or 6 characters long")
        @Pattern(regexp = "^#?([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$", message = "HoverColor must be hexadecimal color value and can start with hashtag")
        @Schema(name = "hoverColor", description = "The hover color of the card", example = "#FFF0C1")
        private String hoverColor;

    }

}
