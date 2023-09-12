package de.bht.planningpoker.repository;

import de.bht.planningpoker.model.Backlog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    boolean existsBySessionPublicId(String sessionId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = { "items" }
    )
    Optional<Backlog> findBySessionPublicId(String sessionId);

}
