package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "team", "team.members", "team.session", "team.session.deck" }
    )
    @Override
    Optional<User> findById(Long id);

}
