package de.bht.planningpoker.auth;

import de.bht.planningpoker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return repository.findById(Long.valueOf(userId))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %s not found", userId)));
    }

}
