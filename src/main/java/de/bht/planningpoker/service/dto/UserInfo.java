package de.bht.planningpoker.service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfo {

    @NotBlank(message = "Name may not be blank")
    @Size(min = 1, max = 32, message = "Name must be between 1 and 32 characters long")
    @Pattern(regexp = "^[A-Za-z ]{1,32}$", message = "Name must be lower or upper case letter from a to z and can have white spaces")
    private String name;

    @NotNull(message = "Active may not be null")
    private boolean active;

    @NotNull(message = "Role may not be null")
    private String role;

}
