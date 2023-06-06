package de.bht.planningpoker.service;

import de.bht.planningpoker.auth.CustomUserDetails;
import de.bht.planningpoker.model.User;
import de.bht.planningpoker.service.exception.ServiceException;
import de.bht.planningpoker.service.exception.UserNotAuthenticatedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getCurrentUser() throws ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }

}
