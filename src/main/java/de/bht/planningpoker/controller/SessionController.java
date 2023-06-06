package de.bht.planningpoker.controller;

import de.bht.planningpoker.auth.jwt.JwtService;
import de.bht.planningpoker.controller.dto.SessionCreateDto;
import de.bht.planningpoker.controller.dto.SessionDto;
import de.bht.planningpoker.controller.dto.SessionJoinDto;
import de.bht.planningpoker.controller.dto.SessionPageDto;
import de.bht.planningpoker.controller.mapper.SessionMapper;
import de.bht.planningpoker.model.Session;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.service.SessionService;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SessionController implements SessionResource {

    private final SessionService service;
    private final SessionMapper mapper;

    private final JwtService jwtService;

    @Override
    public ResponseEntity<SessionPageDto> getPage(Pageable pageable) throws ServiceException {
        Page<Session> paginatedSession = service.paginateSession(pageable);
        return ResponseEntity.ok(mapper.mapToDto(paginatedSession));
    }

    @Override
    public ResponseEntity<SessionDto> getSingle(String id) throws ServiceException {
        Session session = service.getSession(id);
        return ResponseEntity.ok(mapper.mapToDto(session));
    }

    @Override
    public ResponseEntity<SessionDto> create(SessionCreateDto dto) throws ServiceException {
        Session session = service.createSession(mapper.mapToModel(dto));
        User user = session.getTeam().getMember(dto.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("X-Token", jwtService.generateToken(user))
                .header("X-Token-Format", "Bearer")
                .body(mapper.mapToDto(session));
    }

    @Override
    public void delete(String id) throws ServiceException {
        service.deleteSession(id);
    }

    @Override
    public ResponseEntity<SessionDto> join(String id, SessionJoinDto dto) throws ServiceException {
        Session session = service.joinSession(id, mapper.mapToModel(dto));
        User user = session.getTeam().getMember(dto.getUsername());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Token", jwtService.generateToken(user))
                .header("X-Token-Format", "Bearer")
                .body(mapper.mapToDto(session));
    }

    @Override
    public ResponseEntity<SessionDto> leave(String id, User user) throws ServiceException {
        Session session = service.leaveSession(id, user);
        return ResponseEntity.ok(mapper.mapToDto(session));
    }

}
