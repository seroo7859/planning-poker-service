package de.bht.planningpoker.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "DiscussionPostCreate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionPostCreateDto {

    @NotBlank(message = "Content may not be blank")
    @Size(min = 1, max = 512, message = "Content must be between 1 and 512 characters long")
    @Pattern(regexp = "^[A-Za-z0-9 !\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~\\r\\n\\p{So}]{0,256}$", message = "Content must be alphanumeric characters and can have white spaces, punctuations, linebreaks and other symbols")
    @Schema(name = "content", description = "The content of the discussion post", example = "Let's estimate together.")
    private String content;

}
