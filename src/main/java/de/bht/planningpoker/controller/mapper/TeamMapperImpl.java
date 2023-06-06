package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.TeamDto;
import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamDto mapToDto(Team team) {
        return TeamDto.builder()
                .name(team.getName())
                .members(mapToDto(team.getMembers()))
                .build();
    }

    @Override
    public List<TeamDto.TeamMemberDto> mapToDto(List<User> users) {
        return users
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public TeamDto.TeamMemberDto mapToDto(User user) {
        return TeamDto.TeamMemberDto.builder()
                .name(user.getUsername())
                .active(user.isActive())
                .role(user.getRole().toString())
                .build();
    }

}
