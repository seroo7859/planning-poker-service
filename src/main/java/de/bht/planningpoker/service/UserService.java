package de.bht.planningpoker.service;

import de.bht.planningpoker.model.User;
import de.bht.planningpoker.service.exception.ServiceException;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    User getCurrentUser() throws ServiceException;

}
