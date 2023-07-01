package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.TeamDto;
import de.bht.planningpoker.controller.dto.TeamRenameDto;
import de.bht.planningpoker.controller.mapper.TeamMapper;
import de.bht.planningpoker.model.Team;
import de.bht.planningpoker.service.TeamService;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamResource {

    private final TeamService service;
    private final TeamMapper mapper;

    @Override
    public ResponseEntity<TeamDto> getSingle(String sessionId) throws ServiceException {
        Team team = service.getTeam(sessionId);
        return ResponseEntity.ok(mapper.mapToDto(team));
    }

    @Override
    public ResponseEntity<TeamDto> rename(String sessionId, TeamRenameDto dto) throws ServiceException {
        Team renamedTeam = service.renameTeam(sessionId, dto.getName());
        return ResponseEntity.ok(mapper.mapToDto(renamedTeam));
    }
}
