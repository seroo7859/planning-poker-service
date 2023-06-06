package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.TeamDto;
import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.model.User;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface TeamMapper {

    @Valid TeamDto mapToDto(Team team);

    @Valid List<TeamDto.TeamMemberDto> mapToDto(List<User> users);

    @Valid TeamDto.TeamMemberDto mapToDto(User user);

}
