package de.bht.planningpoker.controller;

import de.bht.planningpoker.controller.dto.UserDto;
import de.bht.planningpoker.controller.mapper.UserMapper;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.service.UserService;
import de.bht.planningpoker.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserResource {

    private final UserService service;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<UserDto> getInfo() throws ServiceException {
        User currentUser = service.getCurrentUser();
        return ResponseEntity.ok(mapper.mapToDto(currentUser));
    }

}
