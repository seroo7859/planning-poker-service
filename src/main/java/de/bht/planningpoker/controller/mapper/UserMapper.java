package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.UserDto;
import de.bht.planningpoker.model.User;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserMapper {

    @Valid UserDto mapToDto(User user);

}
