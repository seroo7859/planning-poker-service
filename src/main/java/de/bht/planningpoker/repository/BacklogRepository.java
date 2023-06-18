package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    boolean existsBySessionPublicId(String sessionId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "items" }
    )
    Optional<Backlog> findBySessionPublicId(String sessionId);

}
