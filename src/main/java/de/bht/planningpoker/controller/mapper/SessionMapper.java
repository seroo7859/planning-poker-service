package de.bht.planningpoker.controller.mapper;

import de.bht.planningpoker.controller.dto.SessionCreateDto;
import de.bht.planningpoker.controller.dto.SessionDto;
import de.bht.planningpoker.controller.dto.SessionJoinDto;
import de.bht.planningpoker.controller.dto.SessionPageDto;
import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SessionMapper {

    @Valid SessionPageDto mapToDto(Page<Session> pagedSessions);

    @Valid SessionDto mapToDto(Session session);

    Session mapToModel(@Valid SessionCreateDto sessionCreateDto);

    User mapToModel(@Valid SessionJoinDto sessionJoinDto);

}
