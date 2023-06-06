package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.UserDto;
import de.bht.planningpoker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .name(user.getUsername())
                .active(user.isActive())
                .role(user.getRole().toString())
                .build();
    }

}
